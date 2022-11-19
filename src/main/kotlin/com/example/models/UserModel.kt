package com.example.models

import kotlinx.serialization.Serializable

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
    val firstName: String,
    val lastName: String,
    val patronymic: String?
)

