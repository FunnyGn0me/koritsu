package com.example.koritsu.feature_course.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.koritsu.feature_course.data.remote.responses.Course

@Dao
interface CoursesDao {

    @Query ("SELECT *FROM course")
    fun getCourses(): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

}