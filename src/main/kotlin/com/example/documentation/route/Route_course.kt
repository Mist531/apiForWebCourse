package com.example.documentation.route

import com.example.documentation.CreateCourseTest
import com.example.documentation.GetListCourseTest
import com.example.documentation.ResponseException
import com.example.models.CourseModel
import com.example.models.CreateCourseModel
import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.courseDocs() {
    install(NotarizedRoute()) {
        get = GetInfo.builder {
            tags("Course")
            summary("Получение спска курсов")
            description(
                "Возвращает список курсов. "
            )
            response {
                responseCode(HttpStatusCode.OK)
                responseType</*List<*/CourseModel/*>*/>()
                description("Список курсов")
                examples(
                    "Пример" to GetListCourseTest
                )
            }
        }
        delete = DeleteInfo.builder {
            tags("Admin Course")
            summary("Удаление курса")
            description(
                "Доступно только администратору. Модель для удаления курса: { courseInfoId: UUID } "
            )
            response {
                responseType<HttpStatusCode>()
                responseCode(HttpStatusCode.OK)
                description("Курс удален")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Ошибка")
                examples(
                    "Пример" to ResponseException(
                        message = "Курс не найден"
                    )
                )
            }
        }
        post = PostInfo.builder {
            tags("Admin Course")
            summary("Создание курса")
            description(
                "Доступно только администратору"
            )
            request {
                requestType<CreateCourseModel>()
                description("Модель курса")
                examples(
                    "Пример" to CreateCourseTest
                )
            }
            response {
                responseType<HttpStatusCode>()
                responseCode(HttpStatusCode.OK)
                description("Курс создан")
            }
            canRespond {
                responseType<ResponseException>()
                responseCode(HttpStatusCode.BadRequest)
                description("Ошибка")
                examples(
                    "Пример" to ResponseException(
                        message = "Курс с таким именем уже существует"
                    )
                )
            }
        }
    }
}