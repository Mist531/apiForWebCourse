package com.example.configure

import io.ktor.server.application.*
import org.koin.core.logger.Level
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger
import org.koin.core.module.Module

fun Application.configureKoin(modules: List<Module>) {
    install(Koin) {
        SLF4JLogger(Level.DEBUG)
        modules(
            modules
        )
    }
}