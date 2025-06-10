package com.example.koritsu.feature_course.presentation.course_detail

import com.example.koritsu.feature_course.data.remote.responses.Course

sealed class CourseDetailEvent {
    object AddToWishList:CourseDetailEvent()
    class SaveCurrentState(val course: Course): CourseDetailEvent()
}