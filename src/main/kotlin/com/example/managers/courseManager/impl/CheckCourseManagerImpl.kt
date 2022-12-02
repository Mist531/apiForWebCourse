package com.example.managers.courseManager.impl

import arrow.fx.coroutines.parTraverse
import com.example.database.tables.CourseInfo
import com.example.database.tables.QuestionInfo
import com.example.database.tables.QuestionsInfo
import com.example.managers.courseManager.CheckCourseManager
import com.example.models.CheckCourseModel
import com.example.models.ResultCourseModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CheckCourseManagerImpl : CheckCourseManager {
    override suspend operator fun invoke(param: Unit, request: CheckCourseModel): ResultCourseModel =
        newSuspendedTransaction(Dispatchers.IO) {
            CourseInfo.findById(request.courseInfoId).let { course ->
                if (course != null) {
                    val listWrongQuestion = mutableListOf<String>()
                    var rightAnswer = 0
                    var wrongAnswer = 0
                    request.questions.parTraverse { question ->
                        val questionInfo = QuestionInfo.findById(question.questionsInfoId)
                            ?: throw Exception("Вопрос ${question.questionsInfoId} не найден в курсе ${request.courseInfoId}")
                        if (
                            questionInfo.rightAnswerId ==
                            question.selectAnswerId &&
                            questionInfo.courseInfoId.id.value == request.courseInfoId
                        ) {
                            rightAnswer++
                        } else {
                            listWrongQuestion += question.questionsInfoId.toString()
                            wrongAnswer++
                        }
                    }.let {
                        if (
                            QuestionInfo.find(QuestionsInfo.courseInfoId eq request.courseInfoId).count() ==
                            (rightAnswer + wrongAnswer).toLong()
                        ) {
                            ResultCourseModel(rightAnswer, wrongAnswer, listWrongQuestion)
                        } else {
                            throw Exception("Количество вопросов и ответов не совпадает, проверьте правильность заполнения")
                        }
                    }
                } else {
                    throw Exception("Курс не найден")
                }
            }
        }
}