package com.example.authorization.models

import kotlinx.serialization.Serializable

@Serializable
data class TokensModel(
    val access: String,
    val refresh: String
)