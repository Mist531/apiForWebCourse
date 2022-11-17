package com.example.documentation

import kotlinx.serialization.Serializable

@Serializable
data class ResponseException(
    val message: String
)