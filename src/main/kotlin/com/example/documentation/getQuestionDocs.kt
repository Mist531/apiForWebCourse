package com.example.documentation

import com.example.models.QuestionsInfoModel
import com.example.params.CourseIdModel
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.*

fun Route.getQuestionDocs() {
    install(NotarizedRoute()) {
        security = mapOf(
            "basic" to emptyList()
        )
        post = PostInfo.builder {
            isDeprecated()
            tags("Question")
            summary("GET: Получение всех вопросов")
            description(
                "Возвращает список вопросов по id курса, модель запроса: { courseInfoId: UUID }"
            )
            request {
                requestType<CourseIdModel>()
                description("Модель запроса")
                examples(
                    "Пример" to CourseIdModel(UUID.randomUUID())
                )
            }
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
    }
}