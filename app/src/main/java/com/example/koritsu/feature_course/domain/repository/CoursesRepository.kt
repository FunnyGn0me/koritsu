package com.example.koritsu.feature_course.domain.repository

import com.example.koritsu.feature_course.data.remote.responses.Course

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
    suspend fun saveCourses(courses: List<Course>)
}