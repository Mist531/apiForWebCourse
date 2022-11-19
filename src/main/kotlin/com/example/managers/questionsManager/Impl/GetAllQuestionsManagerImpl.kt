package com.example.managers.questionsManager.Impl

import arrow.fx.coroutines.parTraverse
import com.example.database.tables.AnswerInfo
import com.example.database.tables.AnswersInfo
import com.example.database.tables.QuestionInfo
import com.example.database.tables.QuestionsInfo
import com.example.managers.questionsManager.GetAllQuestionsManager
import com.example.models.AnswerModel
import com.example.models.QuestionsInfoModel
import com.example.params.CourseIdModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GetAllQuestionsManagerImpl : GetAllQuestionsManager {
    override suspend operator fun invoke(param: CourseIdModel, request: Unit): List<QuestionsInfoModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            QuestionInfo.find {
                QuestionsInfo.courseInfoId eq param.courseInfoId
            }.let { listQuestion ->
                if (listQuestion.empty()) {
                    throw Exception("Вопросы не найдены")
                } else {
                    listQuestion.parTraverse { question ->
                        QuestionsInfoModel(
                            questionInfoId = question.id.value,
                            question = question.question,
                            courseInfoId = question.courseInfoId.id.value,
                            rightAnswerId = question.rightAnswerId,
                            listAnswer = AnswerInfo.find {
                                AnswersInfo.questionId eq question.id.value
                            }.parTraverse { answer ->
                                AnswerModel(
                                    answerInfoId = answer.id.value,
                                    answer = answer.answer,
                                    questionInfoId = answer.questionId.id.value
                                )
                            }
                        )
                    }
                }
            }
        }
}