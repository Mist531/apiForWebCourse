package com.example.modules

import com.example.mapper.UserMapper
import org.koin.dsl.module

val mapperModule = module {
    single { UserMapper() }
}
