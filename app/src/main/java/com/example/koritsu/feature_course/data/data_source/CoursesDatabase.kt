package com.example.koritsu.feature_course.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.koritsu.feature_course.data.remote.responses.Course

@Database(
    entities = [Course::class],
    version = 1
)
abstract class CoursesDatabase: RoomDatabase() {
    abstract val coursesDao: CoursesDao

    companion object{
        const val DATABASE_NAME = "courses_db"
    }
}