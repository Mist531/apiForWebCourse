package com.example.documentation.route

import com.example.documentation.AddQuestionsInfoModelTest
import com.example.documentation.GetAllQuestionsModelTest
import com.example.documentation.ResponseException
import com.example.models.AddQuestionsInfoModel
import com.example.models.QuestionsInfoModel
import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.questionDocs() {
    install(NotarizedRoute()) {
        security = mapOf(
            "basic" to emptyList()
        )
        get = GetInfo.builder {
            tags("Question")
            summary("Получение всех вопросов")
            description(
                "Возвращает список вопросов по id курса, модель запроса: { courseInfoId: UUID }"
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
            summary("Добавление вопроса")
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
            summary("Удаление вопроса")
            description(
                "Доступно только администратору. Удаляет вопрос по id вопроса," +
                        " модель запроса: \n{ questionInfoId: UUID }"
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
    }
}