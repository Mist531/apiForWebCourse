package com.example.documentation.route

import com.example.documentation.ResponseException
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.healthcheckDocs() {
    install(NotarizedRoute()) {
        get = GetInfo.builder {
            tags("MainApi")
            summary("Проверка работы сервера")
            description(
                "Возвращает HttpStatusCode\n"
            )
            response {
                responseCode(HttpStatusCode.OK)
                responseType<HttpStatusCode>()
                description("Сервер работает")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Сервер не работает")
            }
        }
    }
}