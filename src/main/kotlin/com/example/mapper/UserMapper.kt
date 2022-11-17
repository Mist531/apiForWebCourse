package com.example.mapper

import com.example.database.models.GetUserModel
import com.example.database.tables.User

class UserMapper : Mapper<User, GetUserModel> {
    override fun invoke(input: User): GetUserModel {
        return GetUserModel(
            firstName = input.firstName,
            lastName = input.lastName,
            patronymic = input.patronymic
        )
    }
}