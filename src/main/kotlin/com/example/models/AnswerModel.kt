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
