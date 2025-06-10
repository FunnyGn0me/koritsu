package com.example.koritsu.feature_course.presentation.account

import com.example.koritsu.feature_course.data.remote.responses.Course

sealed class AccountEvent {
    class AddToWishList(val targetCourse: Course) : AccountEvent()
}