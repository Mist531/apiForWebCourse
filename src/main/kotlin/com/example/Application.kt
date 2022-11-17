package com.example

import com.example.authorization.AuthUtil
import com.example.authorization.models.TokensModel
import com.example.configure.configure
import com.example.database.tables.AnswersInfo
import com.example.database.tables.CoursesInfo
import com.example.database.tables.QuestionsInfo
import com.example.database.tables.Users
import com.example.documentation.route.healthcheckDocs
import com.example.documentation.route.user.userInfoDocs
import com.example.documentation.route.user.userLoginDocs
import com.example.documentation.route.user.userRefreshDocs
import com.example.documentation.route.user.userRegisterDocs
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
    val port = System.getenv("PORT")?.toInt() ?: 8080
    val host = System.getenv("HOST") ?: "0.0.0.0"
    Database.connect(url, "org.postgresql.Driver", user, pass)
    CoroutineScope(Dispatchers.IO).launch {
        newSuspendedTransaction(Dispatchers.IO) {
            SchemaUtils.createMissingTablesAndColumns(
                AnswersInfo, QuestionsInfo, CoursesInfo, Users
            )
        }
    }
    embeddedServer(
        Netty, port = port, host = host,
    ){
        configure(
            port = port,
            host = host,
            listModules = listOf(
                managerModule, mapperModule, managersImplModule
            )
        )
        val userManager: UserManager by inject()
        routing {
            redoc()
            route("api") {
                route("healthcheck") {
                    healthcheckDocs()
                    get {
                        call.respond(HttpStatusCode.OK)
                    }
                }
                route("user") {
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
                    authenticate("jwt") {
                        route("info") {
                            userInfoDocs()
                            get {
                                call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
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
                }
            }
        }

    }.start(wait = true)
}
