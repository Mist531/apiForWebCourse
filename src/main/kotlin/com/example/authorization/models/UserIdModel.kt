package com.example.authorization.models

import com.example.database.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserIdModel(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
)