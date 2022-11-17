package com.example.documentation

import com.example.authorization.models.LoginModel
import com.example.database.models.GetUserModel
import com.example.database.models.RegisterUserModel

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