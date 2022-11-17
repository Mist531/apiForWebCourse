package com.example.managers.usersManager.impl

import com.example.authorization.models.LoginModel
import com.example.authorization.models.UserIdModel
import com.example.database.tables.User
import com.example.database.tables.Users
import com.example.managers.usersManager.LoginUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class LoginUserManagerImpl : LoginUserManager {
    override suspend fun invoke(param: Unit, request: LoginModel): UserIdModel =
        newSuspendedTransaction(Dispatchers.IO) {
            (User.find { Users.login eq request.login and (Users.password eq request.password) }.firstOrNull()
                ?: throw Exception("Пользователь не найден")).let {
                return@newSuspendedTransaction UserIdModel(it.id.value)
            }
        }
}