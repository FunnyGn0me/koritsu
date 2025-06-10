package com.example.koritsu.di

import android.app.Application
import androidx.room.Room
import com.example.koritsu.feature_course.data.data_source.CoursesDatabase
import com.example.koritsu.feature_course.data.remote.CoursesApi
import com.example.koritsu.feature_course.data.repository.CachedCoursesRepositoryImpl
import com.example.koritsu.feature_course.data.repository.CoursesRepositoryImpl
import com.example.koritsu.feature_course.domain.repository.CoursesRepository
import com.example.koritsu.feature_course.domain.use_case.courses_use_cases.CoursesUseCases
import com.example.koritsu.feature_course.domain.use_case.courses_use_cases.GetCourses
import com.example.koritsu.feature_course.domain.use_case.courses_use_cases.SaveCourses
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(app: Application): CoursesDatabase{
        return Room.databaseBuilder(
            app,
            CoursesDatabase::class.java,
            CoursesDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    @Named("remote")
    fun provideCoursesRepository(
        api: CoursesApi
    ): CoursesRepository {
        return CoursesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCoursesUseCases(
        @Named("remote") coursesRepositoryRemote: CoursesRepository,
        @Named("local") coursesRepositoryLocal: CoursesRepository
    ): CoursesUseCases {
        return CoursesUseCases(
            getCourses = GetCourses(coursesRepositoryRemote),
            getCoursesFromCache = GetCourses(coursesRepositoryLocal),
            saveCoursesInCache = SaveCourses(coursesRepositoryLocal)

        )
    }



    @Provides
    @Singleton
    fun provideApi(): CoursesApi{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/u/0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CoursesApi::class.java)
    }

    @Provides
    @Singleton
    @Named("local")
    fun provideCoursesRepositoryCache(coursesDatabase: CoursesDatabase): CoursesRepository{
        return CachedCoursesRepositoryImpl(coursesDatabase)
    }
}