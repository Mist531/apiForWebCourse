package com.example.models

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

@Serializable
data class CreateCourseModel(
    val name:String,
    val description:String?
)

@Serializable
data class CourseIdModel(
    @Serializable(with = UUIDSerializer::class)
    val courseInfoId: UUID,
)
