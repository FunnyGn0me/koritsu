package com.example.koritsu.feature_course.data.repository

import com.example.koritsu.feature_course.data.remote.CoursesApi
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.domain.repository.CoursesRepository

class CoursesRepositoryImpl(private val api: CoursesApi): CoursesRepository {
    override suspend fun getCourses(): List<Course>{

        return api
            .getCourses(id = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q" , mode = "download")
            .courses


    }

    override suspend fun saveCourses(courses: List<Course>) {
    }
}