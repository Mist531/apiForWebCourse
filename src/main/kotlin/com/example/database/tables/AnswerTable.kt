package com.example.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object AnswersInfo: UUIDTable("AnswerInfo", "answerInfoId") {
    val answer = varchar("answer", 100)
    val questionId = reference("questionInfoId", QuestionsInfo)
}

class AnswerInfo(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AnswerInfo>(AnswersInfo)

    var answer by AnswersInfo.answer
    var questionId by QuestionInfo referencedOn AnswersInfo.questionId
}