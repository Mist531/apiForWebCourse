package com.example.documentation.route

import com.example.documentation.PostAnswerModelTest
import com.example.documentation.PutAnswerModelTest
import com.example.documentation.ResponseException
import com.example.models.PostAnswerModel
import com.example.models.PutAnswerModel
import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.metadata.PutInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.bkbn.kompendium.json.schema.definition.TypeDefinition
import io.bkbn.kompendium.oas.payload.Parameter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.*

fun Route.answerDocs() {
    install(NotarizedRoute()) {
        post = PostInfo.builder {
            //isDeprecated()
            tags("Admin Answer")
            summary("Добавить ответ к вопросу")
            description(
                "Доступно только администратору\n"
            )
            request {
                description("Модель ответа")
                requestType<PostAnswerModel>()
                examples(
                    "Пример" to PostAnswerModelTest
                )
            }
            response {
                responseCode(HttpStatusCode.OK)
                responseType<HttpStatusCode>()
                description("Ответ успешно добавлен")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Ответ не добавлен")
                examples(
                    "Пример 1" to ResponseException(
                        "Вопрос не найден"
                    ),
                    "Пример 2" to ResponseException(
                        "Такой ответ уже существует"
                    )
                )
            }
        }
        delete = DeleteInfo.builder {
            //isDeprecated()
            parameters(
                Parameter(
                    name = "{questionInfoId}/{answerInfoId}",
                    `in` = Parameter.Location.path,
                    schema = TypeDefinition.UUID,
                    allowEmptyValue = false,
                    required = true,
                    description = "Id курса",
                    examples = mapOf(
                        "Пример" to Parameter.Example(UUID.randomUUID().toString()+"/"+UUID.randomUUID().toString())
                    )
                )
            )
            tags("Admin Answer")
            summary("Удалить ответ")
            description(
                "Доступно только администратору." /*+
                        " Модель для удаления ответа:" +
                        " { questionInfoId: UUID, answerInfoId: UUID }"*/
            )
            response {
                responseCode(HttpStatusCode.OK)
                responseType<HttpStatusCode>()
                description("Ответ успешно удален")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Ответ не удален")
                examples(
                    "Пример 1" to ResponseException(
                        "Вопрос не найден"
                    ),
                    "Пример 2" to ResponseException(
                        "Ответ не найден"
                    ),
                    "Пример 3" to ResponseException(
                        "Нельзя удалить правильный ответ"
                    )
                )
            }
        }
        put = PutInfo.builder {
            //isDeprecated()
            tags("Admin Answer")
            summary("Изменить ответ")
            description(
                "Доступно только администратору."
            )
            request {
                description("Модель ответа")
                requestType<PutAnswerModel>()
                examples(
                    "Пример" to PutAnswerModelTest
                )
            }
            response {
                responseCode(HttpStatusCode.OK)
                responseType<HttpStatusCode>()
                description("Ответ успешно изменен")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Ответ не изменен")
                examples(
                    "Пример 1" to ResponseException(
                        "Вопрос не найден"
                    ),
                    "Пример 2" to ResponseException(
                        "Ответ не найден"
                    ),
                    "Пример 3" to ResponseException(
                        "Такой ответ уже существует"
                    )
                )
            }
        }
    }
}