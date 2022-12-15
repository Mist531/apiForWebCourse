package com.example.managers.questionsManager

import com.example.managers.SimpleManager
import com.example.models.AddQuestionsInfoModel
import com.example.models.PutQuestionsInfoModel
import com.example.models.QuestionsInfoModel
import com.example.params.CourseIdModel
import com.example.params.QuestionIdModel
import io.ktor.http.*

interface QuestionsManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface GetAllQuestionsManager : QuestionsManager<CourseIdModel, Unit, List<QuestionsInfoModel>>

interface AddQuestionManager : QuestionsManager<Unit, AddQuestionsInfoModel, HttpStatusCode>

interface DeleteQuestionManager : QuestionsManager<Unit, QuestionIdModel, HttpStatusCode>

interface PutQuestionManager : QuestionsManager<Unit, PutQuestionsInfoModel, HttpStatusCode>