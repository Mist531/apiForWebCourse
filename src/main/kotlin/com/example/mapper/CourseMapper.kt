package com.example.mapper

import com.example.database.tables.CourseInfo
import com.example.models.CourseModel

class CourseMapper : Mapper<CourseInfo, CourseModel> {
    override fun invoke(input: CourseInfo): CourseModel {
        return CourseModel(
            courseInfoId = input.id.value,
            name = input.name,
            description = input.description
        )
    }
}