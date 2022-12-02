package com.example.managers.answerManager.Impl

import com.example.database.tables.AnswerInfo
import com.example.database.tables.AnswersInfo
import com.example.database.tables.QuestionInfo
import com.example.managers.answerManager.PostAnswersManager
import com.example.models.PostAnswerModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostAnswersManagerImpl : PostAnswersManager {
    override suspend operator fun invoke(param: Unit, request: PostAnswerModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            QuestionInfo.findById(request.questionInfoId)?.let { question ->
                AnswerInfo.find {
                    (AnswersInfo.answer eq request.answer) and
                            (AnswersInfo.questionInfoId eq request.questionInfoId)
                }.firstOrNull()?.let {
                    throw Exception("Такой ответ уже существует")
                } ?: AnswerInfo.new {
                    answer = request.answer
                    questionInfoId = question
                }
            } ?: throw Exception("Вопрос не найден")
            HttpStatusCode.OK
        }
}