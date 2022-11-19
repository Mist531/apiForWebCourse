package com.example.managersImpl

import com.example.managers.courseManager.CreateCourseManager
import com.example.managers.courseManager.DeleteCourseManager
import com.example.managers.courseManager.GetAllCourseManager
import com.example.models.CourseIdModel
import com.example.models.CourseModel
import com.example.models.CreateCourseModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CourseManager {
    suspend fun getAllCourse(): List<CourseModel>

    suspend fun createCourse(course: CreateCourseModel): HttpStatusCode

    suspend fun deleteCourse(courseId: CourseIdModel): HttpStatusCode
}

class CourseManagerImpl : CourseManager, KoinComponent {
    override suspend fun getAllCourse(): List<CourseModel> {
        val manager: GetAllCourseManager by inject()
        return runCatching {
            manager.invoke(Unit, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun createCourse(course: CreateCourseModel): HttpStatusCode {
        val manager: CreateCourseManager by inject()
        return runCatching {
            manager.invoke(Unit,course)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun deleteCourse(courseId: CourseIdModel): HttpStatusCode {
        val manager: DeleteCourseManager by inject()
        return runCatching {
            manager.invoke(Unit,courseId)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}