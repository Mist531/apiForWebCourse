package com.example.configure

import io.bkbn.kompendium.oas.serialization.KompendiumSerializersModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.dataconversion.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            serializersModule = KompendiumSerializersModule.module
            encodeDefaults = true
            explicitNulls = false
        })
    }
    install(DataConversion) {
        convert<UUID> {
            decode { values -> values.singleOrNull()?.let { UUID.fromString(it) } ?: UUID.randomUUID() }
            encode { listOf(it.toString()) }
        }
    }
}
