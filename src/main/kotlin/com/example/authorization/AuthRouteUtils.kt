package com.example.authorization

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.component.KoinComponent

class AuthRouteUtils: KoinComponent {
    suspend fun authAdmin(
        call: ApplicationCall,
        ifRight: suspend () -> Unit,
    ) {
        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
            if (com.example.adminId.toString() == id) {
                ifRight()
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        } ?: call.respond(HttpStatusCode.Unauthorized)
    }

    suspend fun authUser(
        call: ApplicationCall,
        ifRight: suspend (id: String) -> Unit,
    ) {
        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
            ifRight(id)
        } ?: call.respond(HttpStatusCode.Unauthorized)
    }
}