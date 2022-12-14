package com.example

import arrow.fx.coroutines.parTraverse
import com.example.authorization.AuthUtil
import com.example.authorization.models.TokensModel
import com.example.configure.configure
import com.example.database.tables.*
import com.example.documentation.getQuestionDocs
import com.example.documentation.route.*
import com.example.documentation.route.user.userInfoDocs
import com.example.documentation.route.user.userLoginDocs
import com.example.documentation.route.user.userRefreshDocs
import com.example.documentation.route.user.userRegisterDocs
import com.example.managersImpl.AnswerManager
import com.example.managersImpl.CourseManager
import com.example.managersImpl.QuestionManager
import com.example.managersImpl.UserManager
import com.example.modules.managerModule
import com.example.modules.managersImplModule
import com.example.modules.mapperModule
import com.example.params.UserId
import io.bkbn.kompendium.core.routes.redoc
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.inject
import java.util.*

fun main() {
    val url = System.getenv("DB_URL") ?: "jdbc:postgresql://db.fhshwyorwexsjojuohkx.supabase.co:5432/postgres"
    val pass = System.getenv("DB_PASS") ?: "UBhfHX9s6hT@Vty"
    val user = System.getenv("DB_USER") ?: "postgres"
    val port = System.getenv("PORT")?.toInt() ?: 8081
    val host = System.getenv("HOST") ?: "0.0.0.0"
    val adminName = "admin"

    val adminId = UUID.fromString("404c889c-c9ef-457d-880a-9f649a2768fd")
    val listDefaultUUIDCoure = listOf<UUID>(
        UUID.fromString("904fa590-68f1-11ed-9022-0242ac120002"),
        UUID.fromString("5feb7899-437a-4be0-9bd9-fb0420c44ae3"),
        UUID.fromString("c8acdcf0-68f1-11ed-9022-0242ac120002")
    )

    Database.connect(url, "org.postgresql.Driver", user, pass)
    CoroutineScope(Dispatchers.IO).launch {
        newSuspendedTransaction(Dispatchers.IO) {
            SchemaUtils.createMissingTablesAndColumns(
                AnswersInfo, QuestionsInfo, CoursesInfo, Users
            )
            if (User.findById(adminId) == null) {
                User.new {
                    this.id._value = adminId
                    this.login = adminName
                    this.password = adminName
                    this.firstName = adminName
                    this.lastName = adminName
                }
            }
            listDefaultUUIDCoure.parTraverse { uuid ->
                if (CourseInfo.findById(uuid) == null) {
                    CourseInfo.new {
                        this.id._value = uuid
                        this.name = "Курс $uuid"
                        this.description = "Описание курса $uuid"
                    }.also { courseInfo ->
                        for (i in 1..(2..4).random()) {
                            val rightId = UUID.randomUUID()
                            QuestionInfo.new {
                                this.courseInfoId = courseInfo
                                this.question = "Вопрос $i"
                                this.rightAnswerId = rightId
                            }.also { questionId ->
                                AnswerInfo.new {
                                    this.id._value = rightId
                                    this.answer = "Ответ 1"
                                    this.questionInfoId = questionId
                                }
                                for (j in 2..(2..5).random()) {
                                    AnswerInfo.new {
                                        this.questionInfoId = questionId
                                        this.answer = "Ответ $j"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    embeddedServer(Netty, port = port, host = host,
        module = {
            myApplicationModule(port = port, host = host, adminId = adminId.toString())
        }
    ).start(wait = true)
}

fun Application.myApplicationModule(port: Int, host: String, adminId: String) {
    configure(
        port = port,
        host = host,
        listModules = listOf(
            managerModule, mapperModule, managersImplModule
        )
    )
    val userManager: UserManager by inject()
    val courseManager: CourseManager by inject()
    val questionManager: QuestionManager by inject()
    val answerManager: AnswerManager by inject()
    routing {
        redoc()
        route("api") {

            //region Main
            route("healthcheck") {
                healthcheckDocs()
                get {
                    call.respond(HttpStatusCode.OK)
                }
            }
            route("login") {
                userLoginDocs()
                post {
                    call.respond(userManager.login(call.receive()))
                }
            }
            route("register") {
                userRegisterDocs()
                post {
                    call.respond(userManager.register(call.receive()))
                }
            }
            //endregion

            authenticate("jwt") {

                //region User
                route("user") {
                    route("info") {
                        userInfoDocs()
                        get {
                            call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                                ?.let { id ->
                                    call.respond(userManager.getUser(UserId(UUID.fromString(id))))
                                } ?: call.respond(HttpStatusCode.Unauthorized)
                        }
                    }
                    route("refresh") {
                        userRefreshDocs()
                        get {
                            call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                                ?.let { id ->
                                    call.respond(
                                        TokensModel(
                                            access = AuthUtil.buildAccess(id),
                                            refresh = AuthUtil.buildRefresh(id)
                                        )
                                    )
                                } ?: call.respond(HttpStatusCode.Unauthorized)
                        }
                    }
                }
                //endregion

                //region Course
                route("course") {
                    courseDocs()
                    post {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(courseManager.createCourse(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    get {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let {
                            call.respond(courseManager.getAllCourse())
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    delete {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(courseManager.deleteCourse(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    put {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(courseManager.updateCourse(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    route("check") {
                        checkCourseDocs()
                        post {
                            call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let {
                                call.respond(courseManager.checkCourse(call.receive()))
                            } ?: call.respond(HttpStatusCode.Unauthorized)
                        }
                    }
                }
                //endregion

                //region Question
                route("getQuestion") {
                    getQuestionDocs()
                    post {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let {
                            call.respond(questionManager.getAllQuestion(call.receive()))
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                }
                route("question") {
                    questionDocs()
                    post {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(questionManager.addQuestion(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    get {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let {
                            call.respond(questionManager.getAllQuestion(call.receive()))
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    delete {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(questionManager.deleteQuestion(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    put {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(questionManager.putQuestion(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                }
                //endregion

                //reginon Answer
                route("answer") {
                    answerDocs()
                    post {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(answerManager.postAnswer(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    delete {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(answerManager.deleteAnswer(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                    put {
                        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
                            if (adminId == id) {
                                call.respond(answerManager.putAnswer(call.receive()))
                            } else {
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } ?: call.respond(HttpStatusCode.Unauthorized)
                    }
                }
                //endregion
            }
        }
    }
}
