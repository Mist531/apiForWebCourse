package com.example.managersImpl

import com.example.managers.questionsManager.AddQuestionManager
import com.example.managers.questionsManager.DeleteQuestionManager
import com.example.managers.questionsManager.GetAllQuestionsManager
import com.example.managers.questionsManager.PutQuestionManager
import com.example.models.AddQuestionsInfoModel
import com.example.models.PutQuestionsInfoModel
import com.example.models.QuestionsInfoModel
import com.example.params.CourseIdModel
import com.example.params.QuestionIdModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface QuestionManager {
    suspend fun getAllQuestion(curseId: CourseIdModel): List<QuestionsInfoModel>

    suspend fun addQuestion(question: AddQuestionsInfoModel): HttpStatusCode

    suspend fun deleteQuestion(questionId: QuestionIdModel): HttpStatusCode

    suspend fun putQuestion(question: PutQuestionsInfoModel): HttpStatusCode
}

class QuestionManagerImpl : QuestionManager, KoinComponent {
    override suspend fun getAllQuestion(curseId: CourseIdModel): List<QuestionsInfoModel> {
        val manager: GetAllQuestionsManager by inject()
        return runCatching {
            manager.invoke(curseId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun addQuestion(question: AddQuestionsInfoModel): HttpStatusCode {
        val manager: AddQuestionManager by inject()
        return runCatching {
            manager.invoke(Unit, question)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun deleteQuestion(questionId: QuestionIdModel): HttpStatusCode {
        val manager: DeleteQuestionManager by inject()
        return runCatching {
            manager.invoke(Unit, questionId)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun putQuestion(question: PutQuestionsInfoModel): HttpStatusCode {
        val manager: PutQuestionManager by inject()
        return runCatching {
            manager.invoke(Unit, question)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}