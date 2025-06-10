package com.example.koritsu.feature_course.presentation.courses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.domain.use_case.courses_use_cases.CoursesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val coursesUseCases: CoursesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(
        CoursesScreenState(
            courses = emptyList()
        )
    )

    val state: State<CoursesScreenState> = _state

    private fun loadCourses() {
        viewModelScope.launch {
            val coursesFromCache: List<Course> =
                async(Dispatchers.IO) { coursesUseCases.getCoursesFromCache() }.await()
            if (coursesFromCache.isEmpty()) {
                val courses: List<Course>
                try {
                    courses = coursesUseCases.getCourses()
                    _state.value =
                        state.value.copy(courses = courses)
                    async(Dispatchers.IO) { coursesUseCases.saveCoursesInCache(courses) }.await()
                } catch (exception: Throwable) {
                    println("Interrupted")
                }
            } else {
                _state.value = state.value.copy(courses = coursesFromCache)
            }
        }
    }

    fun onEvent(event: CoursesEvent) {
        when (event) {
            is CoursesEvent.AddToWishList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val changedCoursesFromCache: List<Course>
                    try {
                        changedCoursesFromCache = coursesUseCases.getCoursesFromCache()

                        val targetIndex = changedCoursesFromCache.indexOfFirst { it.id == event.targetCourse.id }

                        changedCoursesFromCache[targetIndex].hasLike =
                            !changedCoursesFromCache[targetIndex].hasLike

                        _state.value =
                            state.value.copy(courses = changedCoursesFromCache)

                        async(Dispatchers.IO) {
                            coursesUseCases.saveCoursesInCache(
                                changedCoursesFromCache
                            )
                        }.await()
                    } catch (exception: Throwable) {
                        println("$exception")
                    }
                }
            }
        }
    }

    fun refresh() {
        loadCourses()
    }
}