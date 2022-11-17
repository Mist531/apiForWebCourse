package com.example.database.models

import com.example.database.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CourseModel(
    @Serializable(with = UUIDSerializer::class)
    val courseInfoId: UUID,
    val name:String,
    val description:String?
)
