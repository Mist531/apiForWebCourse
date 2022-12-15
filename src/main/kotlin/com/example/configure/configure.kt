package com.example.configure

import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.koin.core.module.Module

fun Application.configure(
    host: String,
    port: Int,
    listModules: List<Module>
) {
    install(Resources)
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