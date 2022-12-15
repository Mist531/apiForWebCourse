package com.example.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object QuestionsInfo : UUIDTable("QuestionInfo", "questionInfoId") {
    val question = text("question")
    val courseInfoId = reference("courseInfoId", CoursesInfo)
    val rightAnswerId = uuid("rightAnswerId")
}

class QuestionInfo(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<QuestionInfo>(QuestionsInfo)

    var question by QuestionsInfo.question
    var courseInfoId by CourseInfo referencedOn QuestionsInfo.courseInfoId
    var rightAnswerId by QuestionsInfo.rightAnswerId
}