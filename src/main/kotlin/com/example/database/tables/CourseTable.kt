package com.example.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object CoursesInfo : UUIDTable("CourseInfo", "courseInfoId") {
    val name = text("name")
    val description = text("description").nullable()
}

class CourseInfo(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CourseInfo>(CoursesInfo)

    var name by CoursesInfo.name
    var description by CoursesInfo.description
}