package com.example.koritsu.feature_course.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.presentation.courses.CoursesEvent
import com.example.koritsu.feature_course.presentation.shared.CourseCard
import com.example.koritsu.ui.theme.dark
import com.example.koritsu.ui.theme.darkGray
import com.example.koritsu.ui.theme.stroke
import com.example.koritsu.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    innerPadding: PaddingValues,
    viewModel: AccountViewModel = hiltViewModel(),
    onNavigateToCourseDetailScreen: (course: Course) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    val state: AccountScreenState by remember { viewModel.state }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(dark)
            .padding(innerPadding)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)

    ) {
        Text(
            text = "Профиль",
            style = MaterialTheme.typography.titleLarge,
            color = white,
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
        Box(
           Modifier
                .background(darkGray, RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column() {
                TextIconClickableChip(text = "Написать в поддержку", icon = Icons.Default.KeyboardArrowRight, onClick = {})
                Divider(color = stroke)
                TextIconClickableChip(text = "Настройки", icon = Icons.Default.KeyboardArrowRight, onClick = {})
                Divider(color = stroke)
                TextIconClickableChip(text = "Выйти из аккаунта", icon = Icons.Default.KeyboardArrowRight, onClick = {})
            }
        }
        Text(
            text = "Ваши курсы",
            style = MaterialTheme.typography.titleLarge,
            color = white,
            modifier = Modifier
                .padding(vertical = 16.dp)
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

@Composable
fun TextIconClickableChip(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        color = darkGray,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = text,
                color = white
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = white
            )
        }
    }
}
