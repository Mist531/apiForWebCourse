package com.example.database.models

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
