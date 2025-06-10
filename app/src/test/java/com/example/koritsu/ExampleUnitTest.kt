package com.example.koritsu

import com.example.koritsu.feature_course.data.remote.CoursesApi
import com.example.koritsu.feature_course.data.repository.CoursesRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/u/0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CoursesApi::class.java)

        val coursesRepositoryExample = CoursesRepositoryImpl(api)
        runBlocking {
            coursesRepositoryExample.getCoursesTitles().forEach{print(it)}
        }*/

        assertEquals(4, 2 + 2)
    }
}