package com.example.managers.answerManager

import com.example.managers.SimpleManager
import com.example.models.DeleteAnswerInfoModel
import com.example.models.PostAnswerModel
import com.example.models.PutAnswerModel
import io.ktor.http.*

interface AnswersManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface PutAnswersManager : AnswersManager<Unit, PutAnswerModel, HttpStatusCode>

interface DeleteAnswersManager : AnswersManager<Unit, DeleteAnswerInfoModel, HttpStatusCode>

interface PostAnswersManager : AnswersManager<Unit, PostAnswerModel, HttpStatusCode>
