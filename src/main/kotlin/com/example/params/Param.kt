package com.example.params

import com.example.database.UUIDSerializer
import io.ktor.resources.*
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Resource("")
class Param {
    @Serializable
    @Resource("{userId}")
    data class UserId(
        @Serializable(with = UUIDSerializer::class)
        val userId: UUID,
    )

    @Serializable
    @Resource("{courseInfoId}")
    data class CourseIdModel(
        @Serializable(with = UUIDSerializer::class)
        val courseInfoId: UUID,
    )

    @Serializable
    @Resource("{questionInfoId}")
    data class QuestionIdModel(
        @Serializable(with = UUIDSerializer::class)
        val questionInfoId: UUID,
    )

    @Serializable
    @Resource("{questionInfoId}/{answerInfoId}")
    data class DeleteAnswerInfoModel(
        @Serializable(with = UUIDSerializer::class)
        val questionInfoId: UUID,
        @Serializable(with = UUIDSerializer::class)
        val answerInfoId: UUID
    )
}

@Serializable
data class UserId(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
)

@Serializable
data class CourseIdModel(
    @Serializable(with = UUIDSerializer::class)
    val courseInfoId: UUID,
)

@Serializable
data class QuestionIdModel(
    @Serializable(with = UUIDSerializer::class)
    val questionInfoId: UUID,
)