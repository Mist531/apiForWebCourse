package com.example.authorization.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val login: String,
    val password: String
)