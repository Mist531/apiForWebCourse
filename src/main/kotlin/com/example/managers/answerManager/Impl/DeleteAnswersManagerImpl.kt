package com.example.managers.answerManager.Impl

import com.example.database.tables.AnswerInfo
import com.example.database.tables.QuestionInfo
import com.example.managers.answerManager.DeleteAnswersManager
import com.example.models.DeleteAnswerInfoModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeleteAnswersManagerImpl : DeleteAnswersManager {
    override suspend operator fun invoke(param: Unit, request: DeleteAnswerInfoModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            QuestionInfo.findById(request.questionInfoId)?.let { question ->
                if (question.rightAnswerId == request.answerInfoId) {
                    throw Exception("Нельзя удалить правильный ответ")
                } else {
                    AnswerInfo.findById(request.answerInfoId)?.delete() ?: throw Exception("Ответ не найден")
                }
            } ?: throw Exception("Вопрос не найден")
            HttpStatusCode.OK
        }
}