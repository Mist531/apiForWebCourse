package com.example.configure

import io.ktor.server.application.*
import org.koin.core.module.Module

fun Application.configure(
    host: String,
    port: Int,
    listModules: List<Module>
) {
    configureKoin(
        listModules
    )
    configureLogging()
    configureHTTP()
    configureAuthentication()
    configureRouting()
    configureSerialization()
    configureKompendium(
        host = host,
        port = port
    )
}