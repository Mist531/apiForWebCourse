package com.example.documentation.route

import com.example.documentation.AddQuestionsInfoModelTest
import com.example.documentation.GetAllQuestionsModelTest
import com.example.documentation.PutQuestionsInfoModelTest
import com.example.documentation.ResponseException
import com.example.models.AddQuestionsInfoModel
import com.example.models.PutQuestionsInfoModel
import com.example.models.QuestionsInfoModel
import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.metadata.PutInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.bkbn.kompendium.json.schema.definition.TypeDefinition
import io.bkbn.kompendium.oas.payload.Parameter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.*

fun Route.questionDocs() {
    install(NotarizedRoute()) {
        security = mapOf(
            "basic" to emptyList()
        )
        get = GetInfo.builder {
            tags("Question")
            parameters(
                Parameter(
                    name = "courseInfoId",
                    `in` = Parameter.Location.path,
                    schema = TypeDefinition.UUID,
                    allowEmptyValue = false,
                    required = true,
                    description = "Id курса",
                    examples = mapOf(
                        "Пример" to Parameter.Example(UUID.randomUUID().toString())
                    )
                )
            )
            summary("Получение всех вопросов")
            description(
                "Возвращает список вопросов по id курса"
                //, модель запроса: { courseInfoId: UUID }
            )
            response {
                responseCode(HttpStatusCode.OK)
                responseType<QuestionsInfoModel>()
                description("Вопрос найден")
                examples(
                    "Пример" to GetAllQuestionsModelTest
                )
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Вопрос не найден")
            }
        }
        post = PostInfo.builder {
            tags("Admin Question")
            summary("Добавление вопросов к курсу")
            description(
                "Доступно только администратору. Добавляет вопрос к курсу"
            )
            request {
                requestType<AddQuestionsInfoModel>()
                description("Модель запроса")
                examples(
                    "Пример" to AddQuestionsInfoModelTest
                )
            }
            response {
                responseType<HttpStatusCode>()
                responseCode(HttpStatusCode.OK)
                description("Вопрос добавлен")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Вопрос не добавлен")
                examples(
                    "Пример 1" to ResponseException("Курс не найден"),
                    "Пример 2" to ResponseException("Правильный ответ не найден"),
                    "Пример 3" to ResponseException("Вопрос уже существует")
                )
            }
        }
        delete = DeleteInfo.builder {
            tags("Admin Question")
            parameters(
                Parameter(
                    name = "courseInfoId",
                    `in` = Parameter.Location.path,
                    schema = TypeDefinition.UUID,
                    allowEmptyValue = false,
                    required = true,
                    description = "Id курса",
                    examples = mapOf(
                        "Пример" to Parameter.Example(UUID.randomUUID().toString())
                    )
                )
            )
            summary("Удаление вопроса")
            description(
                "Доступно только администратору. Удаляет вопрос по id вопроса."
                //+ " модель запроса: \n{ questionInfoId: UUID }"
            )
            response {
                responseType<HttpStatusCode>()
                responseCode(HttpStatusCode.OK)
                description("Вопрос удален")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Вопрос не удален")
                examples(
                    "Пример" to ResponseException("Вопрос не найден")
                )
            }
        }
        put = PutInfo.builder {
            tags("Admin Question")
            summary("Изменение вопроса в курсе")
            description(
                "Доступно только администратору."
            )
            request {
                requestType<PutQuestionsInfoModel>()
                description("Модель запроса")
                examples(
                    "Пример" to PutQuestionsInfoModelTest
                )
            }
            response {
                responseType<HttpStatusCode>()
                responseCode(HttpStatusCode.OK)
                description("Вопрос изменен")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Вопрос не изменен")
                examples(
                    "Пример 1" to ResponseException("Вопрос не найден"),
                    "Пример 2" to ResponseException("Правильный ответ не найден"),
                    "Пример 3" to ResponseException("Такой ответ уже существует"),
                    "Пример 4" to ResponseException("Курс не найден"),
                    "Пример 5" to ResponseException("Такой вопрос уже существует")
                )
            }
        }
    }
}