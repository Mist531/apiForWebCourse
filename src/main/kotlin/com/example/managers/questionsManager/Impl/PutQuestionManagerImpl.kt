package com.example.managers.questionsManager.Impl

import arrow.fx.coroutines.parTraverse
import com.example.database.tables.*
import com.example.managers.questionsManager.PutQuestionManager
import com.example.models.PutQuestionsInfoModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PutQuestionManagerImpl : PutQuestionManager {
    override suspend operator fun invoke(param: Unit, request: PutQuestionsInfoModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            CourseInfo.findById(request.courseInfoId)?.let { course ->
                QuestionInfo.findById(request.questionInfoId)?.let { question ->

                    if (
                        question.question != request.question
                    ) {
                        if (
                            QuestionInfo.find {
                                (QuestionsInfo.question eq request.question) and
                                        (QuestionsInfo.courseInfoId eq request.courseInfoId)
                            }.firstOrNull() == null
                        ) {
                            question.question = request.question
                        } else {
                            throw Exception("Такой вопрос уже существует")
                        }
                    }

                    request.listAnswer.parTraverse { answer ->
                        AnswerInfo.findById(answer.answerId)?.let { answerInfo ->
                            if (
                                answerInfo.answer != answer.answer
                            ) {
                                if (
                                    AnswerInfo.find {
                                        (AnswersInfo.answer eq answer.answer) and
                                                (AnswersInfo.questionInfoId eq request.questionInfoId)
                                    }.firstOrNull() == null
                                ) {
                                    answerInfo.answer = answer.answer
                                } else {
                                    throw Exception("Такой ответ уже существует")
                                }
                            }
                        } ?: AnswerInfo.new {
                            this.answer = answer.answer
                            this.questionInfoId = question
                        }
                    }.let {
                        if (
                            question.rightAnswerId != request.rightAnswerId &&
                            AnswerInfo.findById(request.rightAnswerId) != null
                        ) {
                            question.rightAnswerId = request.rightAnswerId
                        } else {
                            throw Exception("Правильный ответ не найден")
                        }
                    }

                } ?: throw Exception("Вопрос не найден")
            } ?: throw Exception("Курс не найден")
            HttpStatusCode.OK
        }
}