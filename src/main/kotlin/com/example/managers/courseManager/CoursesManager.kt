package com.example.managers.courseManager

import com.example.managers.SimpleManager
import com.example.models.CourseIdModel
import com.example.models.CourseModel
import com.example.models.CreateCourseModel
import io.ktor.http.*

interface CoursesManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface GetAllCourseManager : CoursesManager<Unit, Unit, List<CourseModel>>

interface CreateCourseManager : CoursesManager<Unit, CreateCourseModel, HttpStatusCode>

//interface UpdateCourseManager : CourseManager<Unit, Unit, Unit>

interface DeleteCourseManager : CoursesManager<Unit, CourseIdModel, HttpStatusCode>