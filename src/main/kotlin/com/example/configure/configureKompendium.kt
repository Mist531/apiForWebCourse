package com.example.configure

import io.bkbn.kompendium.core.attribute.KompendiumAttributes
import io.bkbn.kompendium.core.plugin.NotarizedApplication
import io.bkbn.kompendium.json.schema.KotlinXSchemaConfigurator
import io.bkbn.kompendium.json.schema.definition.TypeDefinition
import io.bkbn.kompendium.oas.OpenApiSpec
import io.bkbn.kompendium.oas.component.Components
import io.bkbn.kompendium.oas.info.Info
import io.bkbn.kompendium.oas.security.BasicAuth
import io.bkbn.kompendium.oas.server.Server
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.URI
import java.util.*
import kotlin.reflect.typeOf

fun Application.configureKompendium(
    host: String,
    port: Int
) {
    install(NotarizedApplication()) {
        schemaConfigurator = KotlinXSchemaConfigurator()
        openApiJson = {
            route("/openapi.json") {
                get {
                    call.respond(
                        HttpStatusCode.OK,
                        this@route.application.attributes[KompendiumAttributes.openApiSpec]
                    )
                }
            }
        }
        spec = OpenApiSpec(
            components = Components(
                securitySchemes = mutableMapOf(
                    "basic" to BasicAuth()
                )
            ),
            info = Info(
                title = "api--forWebCourse",
                version = "1.0.0",
                description = "EndPoints for api--forWebCourse",
                termsOfService = URI("http://$host:$port/"),
            ),
            servers = mutableListOf(
                Server(
                    description = "Test location",
                    url = URI("http://$host:$port/")
                )
            )
        )
        customTypes = mapOf(
            typeOf<UUID>() to TypeDefinition(type = "string", format = "uuid"),
        )
    }
}