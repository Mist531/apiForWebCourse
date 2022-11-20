package com.example.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object AnswersInfo: UUIDTable("AnswerInfo", "answerInfoId") {
    val answer = text("answer")
    val questionInfoId = reference("questionInfoId", QuestionsInfo)
}

class AnswerInfo(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AnswerInfo>(AnswersInfo)

    var answer by AnswersInfo.answer
    var questionInfoId by QuestionInfo referencedOn AnswersInfo.questionInfoId
}