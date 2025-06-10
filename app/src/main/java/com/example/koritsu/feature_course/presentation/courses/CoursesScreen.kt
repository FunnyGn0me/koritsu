package com.example.koritsu.feature_course.presentation.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.koritsu.R
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.presentation.shared.CourseCard
import com.example.koritsu.ui.theme.dark
import com.example.koritsu.ui.theme.green
import com.example.koritsu.ui.theme.lightGray
import com.example.koritsu.ui.theme.white


@Composable
fun CoursesScreen(
    innerPadding: PaddingValues,
    viewModel: CoursesViewModel = hiltViewModel(),
    onNavigateToCourseDetailScreen: (course: Course) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    val state: CoursesScreenState by remember { viewModel.state }


    Column(
        modifier = Modifier
            .background(dark)
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top = 25.dp)
    ) {

        TopFunctionalBar()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
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
fun TopFunctionalBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            TextField(
                modifier = Modifier.weight(1f),
                value = "",
                onValueChange = {},
                placeholder = { Text("Search courses...") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                },

                colors = TextFieldDefaults.colors(
                    focusedTextColor = white,
                    unfocusedTextColor = white,
                    focusedContainerColor = lightGray,
                    unfocusedContainerColor = lightGray,
                    cursorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(50)
            )

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(28.dp)
                    .background(lightGray, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = null,
                    tint = white
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "По дате добавления ⇅",
                color = green,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}