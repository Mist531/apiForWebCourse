package com.example.database.models

import com.example.database.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AnswerModel(
    @Serializable(with = UUIDSerializer::class)
    val answerInfoId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val questionId:UUID,
    val answer:String
)
