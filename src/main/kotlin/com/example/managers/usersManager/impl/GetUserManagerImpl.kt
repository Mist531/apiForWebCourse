package com.example.managers.usersManager.impl

import com.example.database.models.GetUserModel
import com.example.database.tables.User
import com.example.database.tables.Users
import com.example.managers.usersManager.GetUserManager
import com.example.mapper.UserMapper
import com.example.params.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GetUserManagerImpl(val mapper: UserMapper) : GetUserManager {
    override suspend fun invoke(param: UserId, request: Unit): GetUserModel =
        newSuspendedTransaction(Dispatchers.IO) {
            param.id.let {
                (User.find { Users.id eq it }.firstOrNull()
                    ?: throw Exception("Пользователь не найден")
                        ).let(mapper::invoke)
            }
        }
}