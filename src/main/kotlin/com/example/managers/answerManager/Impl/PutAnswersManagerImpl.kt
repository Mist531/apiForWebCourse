package com.example.managers.answerManager.Impl

import com.example.database.tables.AnswerInfo
import com.example.database.tables.AnswersInfo
import com.example.database.tables.QuestionInfo
import com.example.managers.answerManager.PutAnswersManager
import com.example.models.PutAnswerModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PutAnswersManagerImpl : PutAnswersManager {
    override suspend operator fun invoke(param: Unit, request: PutAnswerModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            QuestionInfo.findById(request.questionInfoId)?.let { question ->
                AnswerInfo.find {
                    (AnswersInfo.answer eq request.answer) and
                            (AnswersInfo.questionInfoId eq request.questionInfoId)
                }.firstOrNull()?.let {
                    throw Exception("Такой ответ уже существует")
                } ?: AnswerInfo.findById(request.answerInfoId)?.let {
                    it.answer = request.answer
                } ?: throw Exception("Ответ не найден")
            } ?: throw Exception("Вопрос не найден")
            HttpStatusCode.OK
        }
}