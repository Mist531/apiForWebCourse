package com.example.documentation

import com.example.authorization.models.LoginModel
import com.example.models.*
import com.example.params.CourseIdModel
import java.util.*

val LoginModelTest = LoginModel(
    login = "test",
    password = "pass"
)

val RegisterUsersModelTest = RegisterUserModel(
    login = "test",
    firstName = "test",
    lastName = "test",
    password = "test",
    patronymic = "test"
)

val PutUsersModelTest = GetUserModel(
    firstName = "test",
    lastName = "test",
    patronymic = "test",
    userId = UUID.randomUUID()
)

val GetListCourseTest = CourseModel(
    courseInfoId = UUID.randomUUID(),
    name = "Курс 1",
    description = "Описание курса 1",
)

val CreateCourseTest = CreateCourseModel(
    name = "Курс 1",
    description = "Описание курса 1",
)

val CourseIdModelTest = CourseIdModel(
    courseInfoId = UUID.randomUUID(),
)

val PutCourseModelTest = PutCourseModel(
    courseInfoId = UUID.randomUUID(),
    name = "Курс 4",
    description = "Описание курса 4",
)

val examplUUID = UUID.randomUUID()

val GetAllQuestionsModelTest = QuestionsInfoModel(
    questionInfoId = UUID.randomUUID(),
    question = "Вопрос 1",
    courseInfoId = UUID.randomUUID(),
    rightAnswerId = examplUUID,
    listAnswer = listOf(
        AnswerModel(
            answerInfoId = examplUUID,
            answer = "Ответ 1",
            questionInfoId = UUID.randomUUID()
        ),
        AnswerModel(
            answerInfoId = UUID.randomUUID(),
            answer = "Ответ 2",
            questionInfoId = UUID.randomUUID()
        ),
        AnswerModel(
            answerInfoId = UUID.randomUUID(),
            answer = "Ответ 3",
            questionInfoId = UUID.randomUUID()
        ),
        AnswerModel(
            answerInfoId = UUID.randomUUID(),
            answer = "Ответ 4",
            questionInfoId = UUID.randomUUID()
        )
    )
)

val AddQuestionsInfoModelTest = AddQuestionsInfoModel(
    question = "Вопрос 1",
    courseInfoId = UUID.randomUUID(),
    rightIndex = 1,
    listAnswer = listOf(
        AddAnswerModel(
            index = 1,
            answer = "Ответ 1"
        ),
        AddAnswerModel(
            index = 2,
            answer = "Ответ 2"
        ),
        AddAnswerModel(
            index = 3,
            answer = "Ответ 3"
        ),
        AddAnswerModel(
            index = 4,
            answer = "Ответ 4"
        )
    )
)

val CheckCourseModelTest = CheckCourseModel(
    courseInfoId = UUID.randomUUID(),
    questions = listOf(
        CheckQuestionModel(
            questionsInfoId = UUID.randomUUID(),
            selectAnswerId = UUID.randomUUID()
        ),
        CheckQuestionModel(
            questionsInfoId = UUID.randomUUID(),
            selectAnswerId = UUID.randomUUID()
        ),
        CheckQuestionModel(
            questionsInfoId = UUID.randomUUID(),
            selectAnswerId = UUID.randomUUID()
        ),
        CheckQuestionModel(
            questionsInfoId = UUID.randomUUID(),
            selectAnswerId = UUID.randomUUID()
        )
    )
)

val PostAnswerModelTest = PostAnswerModel(
    questionInfoId = UUID.randomUUID(),
    answer = "Ответ 1"
)

val PutAnswerModelTest = PutAnswerModel(
    questionInfoId = UUID.randomUUID(),
    answerInfoId = UUID.randomUUID(),
    answer = "Ответ 1"
)

val PutQuestionsInfoModelTest = PutQuestionsInfoModel(
    questionInfoId = UUID.randomUUID(),
    question = "Вопрос 1",
    courseInfoId = UUID.randomUUID(),
    rightAnswerId = examplUUID,
    listAnswer = listOf(
        PutAnswersModels(
            answerId = examplUUID,
            answer = "Ответ 1"
        ),
        PutAnswersModels(
            answerId = UUID.randomUUID(),
            answer = "Ответ 2"
        ),
        PutAnswersModels(
            answerId = UUID.randomUUID(),
            answer = "Ответ 3"
        ),
        PutAnswersModels(
            answerId = UUID.randomUUID(),
            answer = "Ответ 4"
        )
    )
)