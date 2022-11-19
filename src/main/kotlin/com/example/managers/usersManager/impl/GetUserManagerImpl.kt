package com.example.managers.usersManager.impl

import com.example.database.tables.User
import com.example.managers.usersManager.GetUserManager
import com.example.mapper.UserMapper
import com.example.models.GetUserModel
import com.example.params.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GetUserManagerImpl(private val mapper: UserMapper) : GetUserManager {
    override suspend fun invoke(param: UserId, request: Unit): GetUserModel =
        newSuspendedTransaction(Dispatchers.IO) {
            User.findById(param.id)?.let(mapper::invoke)
                ?: throw Exception("Пользователь не найден")
        }
}