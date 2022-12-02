package com.example.documentation.route

import com.example.documentation.CheckCourseModelTest
import com.example.documentation.ResponseException
import com.example.models.CheckCourseModel
import com.example.models.ResultCourseModel
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.*

fun Route.checkCourseDocs() {
    install(NotarizedRoute()) {
        post = PostInfo.builder {
            tags("Course")
            summary("Проверка курса")
            description(
                "Проверяет ответы на курсы, вовзращает кольчиство правильных и не правильных ответов"
            )
            request {
                requestType<CheckCourseModel>()
                description("Модель запроса")
                examples(
                    "Пример" to CheckCourseModelTest
                )
            }
            response {
                responseType<ResultCourseModel>()
                responseCode(HttpStatusCode.OK)
                description("Курс проверен")
                examples(
                    "Пример" to ResultCourseModel(3, 1, listOf("Вопрос 2"))
                )
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Курс не найден или не прошел проверку")
                examples(
                    "Пример 1" to ResponseException("Количество вопросов и ответов не совпадает, проверьте правильность заполнения"),
                    "Пример 2" to ResponseException("Курс не найден"),
                    "Пример 3" to ResponseException("Вопрос ${UUID.randomUUID()} не найден в курсе ${UUID.randomUUID()}")
                )
            }
        }
    }
}