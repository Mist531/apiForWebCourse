package com.example.managers.usersManager

import com.example.authorization.models.LoginModel
import com.example.authorization.models.UserIdModel
import com.example.database.models.GetUserModel
import com.example.database.models.RegisterUserModel
import com.example.managers.SimpleManager
import com.example.params.UserId
import io.ktor.http.*

interface UsersManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface RegisterUserManager : UsersManager<Unit, RegisterUserModel, HttpStatusCode>

interface GetUserManager : UsersManager<UserId, Unit, GetUserModel>

interface LoginUserManager : UsersManager<Unit, LoginModel, UserIdModel>