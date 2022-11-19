package com.example.models

import com.example.database.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class QuestionModel(
    @Serializable(with = UUIDSerializer::class)
    val questionInfoId: UUID,
    val question:String,
    @Serializable(with = UUIDSerializer::class)
    val courseInfoId:UUID,
    @Serializable(with = UUIDSerializer::class)
    val rightAnswerId:UUID
)

/*@Serializable
data class QuestionsInfoIdModel(
    @Serializable(with = UUIDSerializer::class)
    val questionId: UUID
)*/

/*@Serializable
data class GetAllQuestionsModel(
    val listQuestinsInfo: List<QuestionsInfoModel>
)*/

@Serializable
data class QuestionsInfoModel(
    @Serializable(with = UUIDSerializer::class)
    val questionInfoId: UUID,
    val question:String,
    @Serializable(with = UUIDSerializer::class)
    val courseInfoId:UUID,
    @Serializable(with = UUIDSerializer::class)
    val rightAnswerId:UUID,
    val listAnswer: List<AnswerModel>
)

@Serializable
data class AddQuestionsInfoModel(
    val question:String,
    @Serializable(with = UUIDSerializer::class)
    val courseInfoId:UUID,
    val rightIndex:Int,
    val listAnswer: List<AddAnswerModel>
)

@Serializable
data class AddAnswerModel(
    val index: Int,
    val answer:String
)