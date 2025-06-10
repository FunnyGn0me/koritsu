package com.example.koritsu.feature_course.data.remote

import com.example.koritsu.feature_course.data.remote.responses.CoursesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoursesApi {
    @GET("uc")
    suspend fun getCourses(
        @Query("id") id: String,
        @Query("export") mode:String
    ): CoursesApiResponse
}