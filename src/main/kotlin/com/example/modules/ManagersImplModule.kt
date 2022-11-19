package com.example.modules

import com.example.managersImpl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managersImplModule = module {
    singleOf<UserManager>(::UserManagerImpl)
    singleOf<CourseManager>(::CourseManagerImpl)
    singleOf<QuestionManager>(::QuestionManagerImpl)
}
