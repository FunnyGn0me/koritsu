package com.example.koritsu.feature_course.domain.use_case.courses_use_cases

data class CoursesUseCases(
    val getCourses: GetCourses,
    val getCoursesFromCache: GetCourses,
    val saveCoursesInCache: SaveCourses
)