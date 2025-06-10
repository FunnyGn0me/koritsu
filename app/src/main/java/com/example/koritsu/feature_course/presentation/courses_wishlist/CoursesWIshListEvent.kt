package com.example.koritsu.feature_course.presentation.courses_wishlist

import com.example.koritsu.feature_course.data.remote.responses.Course

sealed class CoursesWIshListEvent {
    class AddToWishList(val targetCourse: Course) : CoursesWIshListEvent()
}