package com.example.managers.questionsManager.Impl

import arrow.fx.coroutines.parTraverse
import com.example.database.tables.AnswerInfo
import com.example.database.tables.CourseInfo
import com.example.database.tables.QuestionInfo
import com.example.database.tables.QuestionsInfo
import com.example.managers.questionsManager.AddQuestionManager
import com.example.models.AddQuestionsInfoModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AddQuestionManagerImpl: AddQuestionManager {
    override suspend fun invoke(param: Unit, request: AddQuestionsInfoModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            val findAnswer = QuestionInfo.find {
                QuestionsInfo.question eq request.question
            }.firstOrNull()
            if(findAnswer == null){
                QuestionInfo.new {
                    question = request.question
                    courseInfoId = CourseInfo.findById(request.courseInfoId) ?: throw Exception("Курс не найден")
                    /*rightAnswerId = request.rightAnswerId*/
                }.let {question->
                    var boolean = false
                    request.listAnswer.parTraverse {answerInfo->
                        AnswerInfo.new {
                            answer = answerInfo.answer
                            questionId = question
                        }.let {
                            if (answerInfo.index == request.rightIndex) {
                                //maybe throw exception
                                boolean = true
                                question.rightAnswerId = it.id.value
                            }
                        }
                    }.let {
                        if(!boolean){
                            throw Exception("Правильный ответ не найден")
                        }
                    }
                }
                HttpStatusCode.OK
            }else{
                throw Exception("Вопрос уже существует")
            }
        }
}