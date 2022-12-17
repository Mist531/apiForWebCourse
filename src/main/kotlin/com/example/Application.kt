package com.example

import com.example.authorization.AuthRouteUtils
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
import com.example.models.DeleteAnswerInfoModel
import com.example.modules.commonModule
import com.example.modules.managerModule
import com.example.modules.managersImplModule
import com.example.modules.mapperModule
import com.example.params.CourseIdModel
import com.example.params.Param
import com.example.params.QuestionIdModel
import com.example.params.UserId
import io.bkbn.kompendium.core.routes.redoc
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
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

val adminId: UUID = UUID.fromString("404c889c-c9ef-457d-880a-9f649a2768fd")

fun main() {
    val url = System.getenv("DB_URL") ?: "jdbc:postgresql://db.fhshwyorwexsjojuohkx.supabase.co:5432/postgres"
    val pass = System.getenv("DB_PASS") ?: "UBhfHX9s6hT@Vty"
    val user = System.getenv("DB_USER") ?: "postgres"
    val port = System.getenv("PORT")?.toInt() ?: 8081
    val host = System.getenv("HOST") ?: "0.0.0.0"
    val adminName = "admin"

    val listDefaultUUIDCoure = listOf<UUID>(
        UUID.fromString("904fa590-68f1-11ed-9022-0242ac120002"),
        UUID.fromString("5feb7899-437a-4be0-9bd9-fb0420c44ae3"),
        UUID.fromString("c8acdcf0-68f1-11ed-9022-0242ac120002"),
        UUID.fromString("d0b0b4f0-68f1-11ed-9022-0242ac120002"),
        UUID.fromString("d8b3e1f0-68f1-11ed-9022-0242ac120002"),
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
            listDefaultUUIDCoure.forEach { uuid ->
                if (CourseInfo.findById(uuid) == null) {
                    CourseInfo.new {
                        this.id._value = uuid
                        this.name = "Курс $uuid"
                        this.description = "Описание курса $uuid"
                    }.also { courseInfo ->
                        for (i in 1..(4..7).random()) {
                            val rightId = UUID.randomUUID()
                            QuestionInfo.new {
                                this.courseInfoId = courseInfo
                                this.question = "Вопрос $i"
                                this.rightAnswerId = rightId
                            }.also { questionId ->
                                val countAnswer = (3..6).random()
                                val numberRight = (1..countAnswer).random()
                                for (j in 1..countAnswer) {
                                    AnswerInfo.new {
                                        if (j == numberRight) {
                                            this.id._value = rightId
                                            this.answer = "Ответ $j (right)"
                                        } else {
                                            this.answer = "Ответ $j"
                                        }
                                        this.questionInfoId = questionId
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
            myApplicationModule(port = port, host = host)
        }
    ).start(wait = true)
}

fun Application.myApplicationModule(port: Int, host: String) {

    configure(
        port = port,
        host = host,
        listModules = listOf(
            managerModule, mapperModule, managersImplModule, commonModule
        )
    )

    val userManager: UserManager by inject()
    val courseManager: CourseManager by inject()
    val questionManager: QuestionManager by inject()
    val answerManager: AnswerManager by inject()
    val authRouteUtils: AuthRouteUtils by inject()

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
                            authRouteUtils.authUser(
                                call = call,
                                ifRight = { id ->
                                    call.respond(userManager.getUser(UserId(UUID.fromString(id))))
                                }
                            )
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
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(courseManager.createCourse(call.receive()))
                            }
                        )
                    }
                    get {
                        authRouteUtils.authUser(
                            call = call,
                            ifRight = {
                                call.respond(courseManager.getAllCourse())
                            }
                        )
                    }
                    delete<Param.CourseIdModel> { idCourse ->
                        authRouteUtils.authAdmin(
                            call,
                            ifRight = {
                                call.respond(courseManager.deleteCourse(CourseIdModel(idCourse.courseInfoId)))
                            }
                        )
                    }
                    put {
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(courseManager.updateCourse(call.receive()))
                            }
                        )
                    }
                    route("check") {
                        checkCourseDocs()
                        post {
                            authRouteUtils.authUser(
                                call = call,
                                ifRight = {
                                    call.respond(courseManager.checkCourse(call.receive()))
                                },
                            )
                        }
                    }
                }
                //endregion

                //region Question
                route("getQuestion") {
                    getQuestionDocs()
                    post {
                        authRouteUtils.authUser(
                            call = call,
                            ifRight = {
                                call.respond(questionManager.getAllQuestion(call.receive()))
                            },
                        )
                    }
                }
                route("question") {
                    questionDocs()
                    post {
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(questionManager.addQuestion(call.receive()))
                            }
                        )
                    }
                    get<Param.CourseIdModel> { idCourse ->
                        authRouteUtils.authUser(
                            call = call,
                            ifRight = {
                                call.respond(questionManager.getAllQuestion(CourseIdModel(idCourse.courseInfoId)))
                            }
                        )
                    }
                    delete<Param.QuestionIdModel> { idQuestion ->
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(questionManager.deleteQuestion(QuestionIdModel(idQuestion.questionInfoId)))
                            }
                        )
                    }
                    put {
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(questionManager.putQuestion(call.receive()))
                            }
                        )
                    }
                }
                //endregion

                //reginon Answer
                route("answer") {
                    answerDocs()
                    post {
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(answerManager.postAnswer(call.receive()))
                            }
                        )
                    }
                    delete<Param.DeleteAnswerInfoModel> { deleteModel ->
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(
                                    answerManager.deleteAnswer(
                                        DeleteAnswerInfoModel(
                                            deleteModel.questionInfoId,
                                            deleteModel.answerInfoId
                                        )
                                    )
                                )
                            }
                        )
                    }
                    put {
                        authRouteUtils.authAdmin(
                            call = call,
                            ifRight = {
                                call.respond(answerManager.putAnswer(call.receive()))
                            }
                        )
                    }
                }
                //endregion
            }
        }
    }
}
