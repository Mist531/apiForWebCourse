package com.example

import com.example.configure.configure
import com.example.database.tables.AnswersInfo
import com.example.database.tables.CoursesInfo
import com.example.database.tables.QuestionsInfo
import com.example.database.tables.Users
import com.example.documentation.route.healthcheckDocs
import io.bkbn.kompendium.core.routes.redoc
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun main() {
    val url = System.getenv("DB_URL") ?: "jdbc:postgresql://db.duvjbcyhlexoryxcbisr.supabase.co:5432/postgres"
    val pass = System.getenv("DB_PASS") ?: "22c687fb-aff2-4a55-98c1-8386fc108ebf"
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
            )
        )
        routing {
            redoc()
            route("api") {
                route("healthcheck") {
                    healthcheckDocs()
                    get {
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
        }

    }.start(wait = true)
}
