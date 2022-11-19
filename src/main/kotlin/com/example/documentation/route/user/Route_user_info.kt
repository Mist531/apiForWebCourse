package com.example.documentation.route.user

import com.example.documentation.PutUsersModelTest
import com.example.documentation.ResponseException
import com.example.models.GetUserModel
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.userInfoDocs() {
    install(NotarizedRoute()) {
        security = mapOf(
            "basic" to emptyList()
        )
        get = GetInfo.builder {
            tags("User")
            summary("Получение информации о пользователе")
            description(
                "Возвращает информацию о пользователе, userId берёт из Token\n"
            )
            response {
                responseCode(HttpStatusCode.OK)
                responseType<GetUserModel>()
                description("Информация о пользователе")
                examples(
                    "Пример" to PutUsersModelTest
                )
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Некорректный запрос")
                examples(
                    "Пример" to ResponseException("Пользователь не найден")
                )
            }
        }
    }
}