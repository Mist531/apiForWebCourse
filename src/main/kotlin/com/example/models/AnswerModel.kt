package com.example.models

import com.example.database.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AnswerModel(
    @Serializable(with = UUIDSerializer::class)
    val answerInfoId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val questionInfoId:UUID,
    val answer:String
)

@Serializable
data class PutAnswerModel(
    @Serializable(with = UUIDSerializer::class)
    val questionInfoId:UUID,
    @Serializable(with = UUIDSerializer::class)
    val answerInfoId: UUID,
    val answer:String
)

@Serializable
data class DeleteAnswerInfoModel(
    @Serializable(with = UUIDSerializer::class)
    val questionInfoId:UUID,
    @Serializable(with = UUIDSerializer::class)
    val answerInfoId: UUID
)

@Serializable
data class PostAnswerModel(
    @Serializable(with = UUIDSerializer::class)
    val questionInfoId:UUID,
    val answer:String
)