package com.example.configure

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    install(Authentication) {
        basic("basic") {
            jwt("jwt") {
                realm = System.getenv("JWT_REALM") ?: "test_realm"
                val jwtAudience = System.getenv("JWT_AUDIENCE") ?: "test_audience"
                val secret = System.getenv("JWT_SECRET") ?: "test_secret"
                val domain = System.getenv("JWT_DOMAIN") ?: "test_domain"
                verifier(
                    JWT.require(Algorithm.HMAC256(secret))
                        .withAudience(jwtAudience)
                        .withIssuer(domain)
                        .build()
                )
                validate { credential ->
                    if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
                }
                challenge { _, _ ->
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}
