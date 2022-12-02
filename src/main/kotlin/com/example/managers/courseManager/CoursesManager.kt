package com.example.managers.courseManager

import com.example.managers.SimpleManager
import com.example.models.*
import com.example.params.CourseIdModel
import io.ktor.http.*

interface CoursesManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface GetAllCourseManager : CoursesManager<Unit, Unit, List<CourseModel>>

interface CreateCourseManager : CoursesManager<Unit, CreateCourseModel, HttpStatusCode>

interface UpdateCourseManager : CoursesManager<Unit, PutCourseModel, HttpStatusCode>

interface DeleteCourseManager : CoursesManager<Unit, CourseIdModel, HttpStatusCode>

interface CheckCourseManager : CoursesManager<Unit, CheckCourseModel, ResultCourseModel>