package com.example.models

import com.example.database.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RegisterUserModel(
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String?
)

@Serializable
data class GetUserModel(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val patronymic: String?
)