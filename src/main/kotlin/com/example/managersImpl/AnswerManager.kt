package com.example.managersImpl

import com.example.managers.answerManager.DeleteAnswersManager
import com.example.managers.answerManager.PostAnswersManager
import com.example.managers.answerManager.PutAnswersManager
import com.example.models.DeleteAnswerInfoModel
import com.example.models.PostAnswerModel
import com.example.models.PutAnswerModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AnswerManager {
    suspend fun putAnswer(putAnswerModel: PutAnswerModel): HttpStatusCode

    suspend fun deleteAnswer(deleteAnswerInfoModel: DeleteAnswerInfoModel): HttpStatusCode

    suspend fun postAnswer(postAnswerModel: PostAnswerModel): HttpStatusCode
}

class AnswerManagerImpl : AnswerManager, KoinComponent {
    override suspend fun putAnswer(putAnswerModel: PutAnswerModel): HttpStatusCode {
        val manager: PutAnswersManager by inject()
        return runCatching {
            manager.invoke(Unit, putAnswerModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun deleteAnswer(deleteAnswerInfoModel: DeleteAnswerInfoModel): HttpStatusCode {
        val manager: DeleteAnswersManager by inject()
        return runCatching {
            manager.invoke(Unit, deleteAnswerInfoModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun postAnswer(postAnswerModel: PostAnswerModel): HttpStatusCode {
        val manager: PostAnswersManager by inject()
        return runCatching {
            manager.invoke(Unit, postAnswerModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}