package com.example.managers.usersManager.impl

import com.example.models.RegisterUserModel
import com.example.database.tables.User
import com.example.database.tables.Users
import com.example.managers.usersManager.RegisterUserManager
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RegisterUserManagerImpl : RegisterUserManager {
    override suspend fun invoke(param: Unit, request: RegisterUserModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            (User.find { Users.login eq request.login }.empty()).let { boolean: Boolean ->
                if (!boolean) {
                    throw Exception("Пользователь с таким login уже существует")
                } else {
                    User.new {
                        login = request.login
                        firstName = request.firstName
                        lastName = request.lastName
                        patronymic = request.patronymic
                        password = request.password
                    }.let {
                        return@newSuspendedTransaction HttpStatusCode.OK
                    }
                }

            }
        }
}