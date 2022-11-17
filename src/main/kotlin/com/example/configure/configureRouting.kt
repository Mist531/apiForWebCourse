package com.example.configure

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Exception> { call, cause ->
            call.respond(call.response.status() ?: HttpStatusCode.BadRequest, cause.message.orEmpty())
        }
    }
}
