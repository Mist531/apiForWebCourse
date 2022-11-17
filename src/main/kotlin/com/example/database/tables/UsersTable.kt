package com.example.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Users : UUIDTable("User", "userId") {
    val login = text("login").uniqueIndex()
    val password = varchar("password", 80)
    val firstName = text("firstName")
    val lastName = text("lastName")
    val patronymic = text("patronymic").nullable()
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)

    var login by Users.login
    var firstName by Users.firstName
    var lastName by Users.lastName
    var password by Users.password
    var patronymic by Users.patronymic
}