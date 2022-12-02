package com.example.modules

import com.example.managers.courseManager.*
import com.example.managers.courseManager.impl.*
import com.example.managers.questionsManager.AddQuestionManager
import com.example.managers.questionsManager.DeleteQuestionManager
import com.example.managers.questionsManager.GetAllQuestionsManager
import com.example.managers.questionsManager.Impl.AddQuestionManagerImpl
import com.example.managers.questionsManager.Impl.DeleteQuestionManagerImpl
import com.example.managers.questionsManager.Impl.GetAllQuestionsManagerImpl
import com.example.managers.usersManager.GetUserManager
import com.example.managers.usersManager.LoginUserManager
import com.example.managers.usersManager.RegisterUserManager
import com.example.managers.usersManager.impl.GetUserManagerImpl
import com.example.managers.usersManager.impl.LoginUserManagerImpl
import com.example.managers.usersManager.impl.RegisterUserManagerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managerModule = module {
    //region User
    singleOf(::GetUserManagerImpl) {
        bind<GetUserManager>()
    }
    singleOf(::RegisterUserManagerImpl) {
        bind<RegisterUserManager>()
    }
    singleOf(::LoginUserManagerImpl) {
        bind<LoginUserManager>()
    }
    //endregion

    //region Course
    singleOf(::GetAllCourseManagerImpl) {
        bind<GetAllCourseManager>()
    }
    singleOf(::CreateCourseManagerImpl) {
        bind<CreateCourseManager>()
    }
    singleOf(::DeleteCourseManagerImpl){
        bind<DeleteCourseManager>()
    }
    singleOf(::UpdateCourseManagerImpl){
        bind<UpdateCourseManager>()
    }
    singleOf(::CheckCourseManagerImpl){
        bind<CheckCourseManager>()
    }
    //endregion

    //region Question
    singleOf(::GetAllQuestionsManagerImpl){
        bind<GetAllQuestionsManager>()
    }
    singleOf(::AddQuestionManagerImpl){
        bind<AddQuestionManager>()
    }
    singleOf(::DeleteQuestionManagerImpl){
        bind<DeleteQuestionManager>()
    }
    //endregion
}