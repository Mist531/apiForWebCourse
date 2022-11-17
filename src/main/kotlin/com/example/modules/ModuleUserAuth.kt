package com.example.modules

import com.example.managersImpl.UserManager
import com.example.managersImpl.UserManagerImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managersImplModule = module {
    singleOf<UserManager>(::UserManagerImpl)
}
