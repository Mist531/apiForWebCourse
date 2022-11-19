package com.example.managers.courseManager.impl

import com.example.database.tables.*
import com.example.managers.courseManager.DeleteCourseManager
import com.example.models.CourseIdModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeleteCourseManagerImpl : DeleteCourseManager {
    override suspend operator fun invoke(param: Unit, request: CourseIdModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            CourseInfo.find {
                CoursesInfo.id eq request.courseInfoId
            }.firstOrNull().let { course: CourseInfo? ->
                if (course != null) {
                    QuestionInfo.find {
                        QuestionsInfo.courseInfoId eq request.courseInfoId
                    }.forEach {
                        AnswerInfo.find {
                            AnswersInfo.questionId eq it.id.value
                        }.forEach {
                            it.delete()
                        }
                        it.delete()
                    }
                    course.delete()
                    HttpStatusCode.OK
                } else {
                    throw Exception("Курс не найден")
                }
            }
        }
}