package com.example.koritsu.feature_course.presentation.courses_wishlist


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.presentation.courses.CoursesEvent
import com.example.koritsu.feature_course.presentation.shared.CourseCard
import com.example.koritsu.ui.theme.dark
import com.example.koritsu.ui.theme.white

@Composable
fun CoursesWishlistScreen(
    innerPadding: PaddingValues,
    viewModel: CoursesWishListViewModel = hiltViewModel(),
    onNavigateToCourseDetailScreen: (course: Course) -> Unit
){
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    val state: CoursesWishListScreenState by remember { viewModel.state }

    Column(
        modifier = Modifier
            .background(dark)
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Избранное",
            style = MaterialTheme.typography.titleLarge,
            color = white,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.courses) { course ->
                CourseCard(
                    course = course,
                    onNavigateToCourseScreen = onNavigateToCourseDetailScreen,
                    function = {viewModel.onEvent(CoursesEvent.AddToWishList(course))}
                )
            }
        }
    }
}