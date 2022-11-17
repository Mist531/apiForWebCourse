package com.example.documentation.route.user

import com.example.database.models.RegisterUserModel
import com.example.documentation.RegisterUsersModelTest
import com.example.documentation.ResponseException
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.userRegisterDocs() {
    install(NotarizedRoute()) {
        post = PostInfo.builder {
            tags("User")
            summary("Регистрация пользователя")
            description("Возвращает HttpStatusCode.OK при успешной регистрации пользователя")
            request {
                requestType<RegisterUserModel>()
                description("Модель для регистрации пользователя")
                examples(
                    "Пример" to RegisterUsersModelTest
                )
            }
            response {
                responseType<RegisterUserModel>()
                responseCode(HttpStatusCode.OK)
                description("Успешная регистрация пользователя")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Некорректный запрос")
                examples(
                    "Некорректный ввод поля" to ResponseException("Unexpected JSON token at offset..."),
                    "Пример" to ResponseException("Пользователь с таким login уже существует")
                )
            }
        }
    }
}