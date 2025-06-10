package com.example.koritsu.feature_course.presentation.account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.domain.use_case.courses_use_cases.CoursesUseCases
import com.example.koritsu.feature_course.presentation.courses.CoursesEvent
import com.example.koritsu.feature_course.presentation.courses_wishlist.CoursesWishListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountUseCases: CoursesUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        AccountScreenState(
            courses = emptyList()
        )
    )

    val state: State<AccountScreenState> = _state

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            val coursesWishList: List<Course> =
                async(Dispatchers.IO) { accountUseCases.getCoursesFromCache() }.await()
            _state.value = state.value.copy(courses = coursesWishList.filter { it.hasLike })
        }
    }

    fun onEvent(event: CoursesEvent) {
        when (event) {
            is CoursesEvent.AddToWishList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val changedCoursesFromCache: List<Course>
                    try {
                        changedCoursesFromCache = accountUseCases.getCoursesFromCache()

                        val targetIndex = changedCoursesFromCache.indexOfFirst { it.id == event.targetCourse.id }

                        changedCoursesFromCache[targetIndex].hasLike =
                            !changedCoursesFromCache[targetIndex].hasLike

                        _state.value =
                            state.value.copy(courses = changedCoursesFromCache.filter { it.hasLike })

                        async(Dispatchers.IO) {
                            accountUseCases.saveCoursesInCache(
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