package com.example.documentation

import com.example.authorization.models.LoginModel
import com.example.models.*
import java.util.*

val LoginModelTest = LoginModel(
    login = "test",
    password = "pass"
)

val RegisterUsersModelTest = RegisterUserModel(
    login = "test",
    firstName = "test",
    lastName = "test",
    password = "test",
    patronymic = "test"
)

val PutUsersModelTest = GetUserModel(
    firstName = "test",
    lastName = "test",
    patronymic = "test"
)

val GetListCourseTest = /*listOf(*/
    CourseModel(
        courseInfoId = UUID.randomUUID(),
        name = "Курс 1",
        description = "Описание курса 1",
    )/*,
    CourseModel(
        courseInfoId = UUID.randomUUID(),
        name = "Курс 2",
        description = "Описание курса 2",
    ),
)*/

val CreateCourseTest = CreateCourseModel(
    name = "Курс 1",
    description = "Описание курса 1",
)

val CourseIdModelTest = CourseIdModel(
    courseInfoId = UUID.randomUUID(),
)