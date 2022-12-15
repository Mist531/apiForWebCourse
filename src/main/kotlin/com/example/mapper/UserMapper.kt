package com.example.mapper

import com.example.database.tables.User
import com.example.models.GetUserModel

class UserMapper : Mapper<User, GetUserModel> {
    override fun invoke(input: User): GetUserModel {
        return GetUserModel(
            userId = input.id.value,
            firstName = input.firstName,
            lastName = input.lastName,
            patronymic = input.patronymic
        )
    }
}