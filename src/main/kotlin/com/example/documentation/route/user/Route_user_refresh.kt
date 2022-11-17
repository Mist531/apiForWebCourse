package com.example.documentation.route.user

import com.example.documentation.ResponseException
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.userRefreshDocs() {
    install(NotarizedRoute()) {
        get = GetInfo.builder {
            security = mapOf(
                "basic" to emptyList()
            )
            tags("User")
            summary("Метод обновления токенов доступа")
            description("Для обновления необходимо использовать refresh токен в качестве токена доступа.")
            response {
                responseType<HttpStatusCode>()
                responseCode(HttpStatusCode.OK)
                description("Успешное обновление токена")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Некорректный запрос")
                examples(
                    "Пример" to ResponseException("Неверный refresh токен")
                )
            }
        }
    }
}