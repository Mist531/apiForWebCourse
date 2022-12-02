package com.example.managersImpl

import com.example.managers.courseManager.*
import com.example.models.*
import com.example.params.CourseIdModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CourseManager {
    suspend fun getAllCourse(): List<CourseModel>

    suspend fun createCourse(course: CreateCourseModel): HttpStatusCode

    suspend fun deleteCourse(courseId: CourseIdModel): HttpStatusCode

    suspend fun updateCourse(course: PutCourseModel): HttpStatusCode
    suspend fun checkCourse(checkCourse: CheckCourseModel): ResultCourseModel
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

    override suspend fun updateCourse(course: PutCourseModel): HttpStatusCode {
        val manager: UpdateCourseManager by inject()
        return runCatching {
            manager.invoke(Unit,course)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun checkCourse(checkCourse: CheckCourseModel): ResultCourseModel {
        val manager: CheckCourseManager by inject()
        return runCatching {
            manager.invoke(Unit,checkCourse)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}