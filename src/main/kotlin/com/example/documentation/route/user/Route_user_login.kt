package com.example.documentation.route.user

import com.example.authorization.models.LoginModel
import com.example.authorization.models.TokensModel
import com.example.documentation.LoginModelTest
import com.example.documentation.ResponseException
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.userLoginDocs() {
    install(NotarizedRoute()) {
        post = PostInfo.builder {
            tags("User")
            summary("Авторизация пользователя, в качестве логина используется почта")
            description("Возвращает HttpStatusCode.OK при успешной авторизации пользователя")
            request {
                requestType<LoginModel>()
                description("Модель для авторизации пользователя")
                examples(
                    "Пример" to LoginModelTest
                )
            }
            response {
                responseType<TokensModel>()
                responseCode(HttpStatusCode.OK)
                description("Успешная авторизация пользователя, возвращает токен access и refresh Token")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Некорректный запрос")
                examples(
                    "Некорректный ввод поля" to ResponseException("Unexpected JSON token at offset..."),
                    "Пример" to ResponseException("Пользователь не найден")
                )
            }
        }
    }
}