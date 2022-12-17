package com.example.modules

import com.example.authorization.AuthRouteUtils
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::AuthRouteUtils)
}