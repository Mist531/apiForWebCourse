package com.example.params

import com.example.database.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserId(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
)

