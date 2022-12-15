package com.example.managers.questionsManager.Impl

import arrow.fx.coroutines.parTraverse
import com.example.database.tables.*
import com.example.managers.questionsManager.DeleteQuestionManager
import com.example.params.QuestionIdModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeleteQuestionManagerImpl : DeleteQuestionManager {
    override suspend operator fun invoke(param: Unit, request: QuestionIdModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            val question = QuestionInfo.find {
                QuestionsInfo.id eq request.questionInfoId
            }.firstOrNull() ?: throw Exception("Вопрос не найден")
            QuestionInfo.find {
                CoursesInfo.id eq question.courseInfoId.id
            }.count().let {
                if (it == 1L) {
                    throw Exception("Нельзя удалить последний вопрос")
                }
            }
            question.id.value.let { questionId ->
                AnswerInfo.find {
                    AnswersInfo.questionInfoId eq questionId
                }.parTraverse {
                    it.delete()
                }
                question.delete()
            }
            HttpStatusCode.OK
        }
}
