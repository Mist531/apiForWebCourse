package com.example.managers.courseManager.impl

import com.example.database.tables.CourseInfo
import com.example.managers.courseManager.GetAllCourseManager
import com.example.mapper.CourseMapper
import com.example.models.CourseModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GetAllCourseManagerImpl(private val mapper: CourseMapper) : GetAllCourseManager {
    override suspend operator fun invoke(param: Unit, request: Unit): List<CourseModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            return@newSuspendedTransaction CourseInfo.all().map(mapper::invoke)
        }
}