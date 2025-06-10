package com.example.koritsu.feature_course.presentation.course_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.domain.use_case.courses_use_cases.CoursesUseCases
import com.example.koritsu.feature_course.presentation.Routes
import com.example.koritsu.feature_course.presentation.Routes.CourseDetailScreenRoute.Companion.toRoute
import com.example.koritsu.feature_course.presentation.courses.CoursesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val courseDetailUseCases: CoursesUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(
        CourseDetailScreenState(
            Course(
                hasLike = false,
                id = 0,
                price = "",
                publishDate = "",
                rate = "",
                startDate = "",
                text = "",
                title = ""
            )
        )
    )

    val state: State<CourseDetailScreenState> = _state


    fun onEvent(event: CourseDetailEvent) {
        when (event) {
            is CourseDetailEvent.AddToWishList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val changedCoursesFromCache: List<Course>
                    try {
                        changedCoursesFromCache = courseDetailUseCases.getCoursesFromCache()

                        val targetIndex = changedCoursesFromCache.indexOfFirst { it.id == _state.value.course.id }

                        changedCoursesFromCache[targetIndex].hasLike =
                            !changedCoursesFromCache[targetIndex].hasLike

                        _state.value =
                            state.value.copy(course = changedCoursesFromCache[targetIndex])

                        async(Dispatchers.IO) {
                            courseDetailUseCases.saveCoursesInCache(
                                changedCoursesFromCache
                            )
                        }.await()
                    } catch (exception: Throwable) {
                        println("$exception")
                    }
                }
            }

            is CourseDetailEvent.SaveCurrentState -> {
                _state.value =
                    state.value.copy(course = event.course)
            }
        }
    }
}