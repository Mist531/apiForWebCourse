package com.example.managers.courseManager.impl

import com.example.database.tables.CourseInfo
import com.example.database.tables.CoursesInfo
import com.example.managers.courseManager.CreateCourseManager
import com.example.models.CreateCourseModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CreateCourseManagerImpl : CreateCourseManager {
    override suspend operator fun invoke(param: Unit, request: CreateCourseModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            CourseInfo.find {
                CoursesInfo.name eq request.name
            }.firstOrNull().let { course: CourseInfo? ->
                if (course == null) {
                    CourseInfo.new {
                        this.name = request.name
                        this.description = request.description
                    }
                    HttpStatusCode.OK
                } else {
                    throw Exception("Курс с таким именем уже существует")
                }
            }
        }
}