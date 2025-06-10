package com.example.koritsu.feature_course.domain.use_case.courses_use_cases

import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.domain.repository.CoursesRepository

class SaveCourses (val coursesRepository: CoursesRepository){
    suspend operator fun invoke(courses: List<Course>){
        coursesRepository.saveCourses(courses)
    }
}