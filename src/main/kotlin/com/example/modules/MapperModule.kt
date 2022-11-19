package com.example.modules

import com.example.mapper.CourseMapper
import com.example.mapper.UserMapper
import org.koin.dsl.module

val mapperModule = module {
    single { UserMapper() }
    single { CourseMapper() }
}
