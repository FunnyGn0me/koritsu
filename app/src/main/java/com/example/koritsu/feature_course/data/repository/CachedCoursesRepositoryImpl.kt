package com.example.koritsu.feature_course.data.repository

import com.example.koritsu.feature_course.data.data_source.CoursesDatabase
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.domain.repository.CoursesRepository

class CachedCoursesRepositoryImpl(private val coursesDatabase: CoursesDatabase): CoursesRepository{
    override suspend fun getCourses(): List<Course> {
        return coursesDatabase
            .coursesDao
            .getCourses()
    }

    override suspend fun saveCourses(courses: List<Course>){
        coursesDatabase.coursesDao.insertCourses(courses)
    }
}