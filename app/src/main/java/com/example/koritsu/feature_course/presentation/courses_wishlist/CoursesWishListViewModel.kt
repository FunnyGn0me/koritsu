package com.example.koritsu.feature_course.presentation.courses_wishlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.domain.use_case.courses_use_cases.CoursesUseCases
import com.example.koritsu.feature_course.presentation.courses.CoursesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesWishListViewModel @Inject constructor(
    private val coursesWishlistUseCases: CoursesUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        CoursesWishListScreenState(
            courses = emptyList()
        )
    )

    val state: State<CoursesWishListScreenState> = _state

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            val coursesWishList: List<Course> =
                async(Dispatchers.IO) { coursesWishlistUseCases.getCoursesFromCache() }.await()
            _state.value = state.value.copy(courses = coursesWishList.filter { it.hasLike })
        }
    }

    fun onEvent(event: CoursesEvent) {
        when (event) {
            is CoursesEvent.AddToWishList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val changedCoursesFromCache: List<Course>
                    try {
                        changedCoursesFromCache = coursesWishlistUseCases.getCoursesFromCache()

                        val targetIndex = changedCoursesFromCache.indexOfFirst { it.id == event.targetCourse.id }

                        changedCoursesFromCache[targetIndex].hasLike =
                            !changedCoursesFromCache[targetIndex].hasLike

                        _state.value =
                            state.value.copy(courses = changedCoursesFromCache.filter { it.hasLike })

                        async(Dispatchers.IO) {
                            coursesWishlistUseCases.saveCoursesInCache(
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