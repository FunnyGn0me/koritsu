package com.example.koritsu.feature_course.presentation.courses

import com.example.koritsu.feature_course.data.remote.responses.Course

sealed class CoursesEvent {
    class AddToWishList(val targetCourse: Course) : CoursesEvent()
}