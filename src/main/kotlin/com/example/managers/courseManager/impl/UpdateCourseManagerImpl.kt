package com.example.managers.courseManager.impl

import com.example.database.tables.CourseInfo
import com.example.managers.courseManager.UpdateCourseManager
import com.example.models.PutCourseModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UpdateCourseManagerImpl : UpdateCourseManager {
    override suspend operator fun invoke(param: Unit, request: PutCourseModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            val course = CourseInfo.findById(request.courseInfoId) ?: throw Exception("Курс не найден")
            course.name = request.name ?: course.name
            course.description = request.description ?: course.description
            return@newSuspendedTransaction HttpStatusCode.OK
        }
}