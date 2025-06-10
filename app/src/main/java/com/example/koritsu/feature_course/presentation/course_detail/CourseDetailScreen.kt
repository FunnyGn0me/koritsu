package com.example.koritsu.feature_course.presentation.course_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.koritsu.R
import com.example.koritsu.ui.theme.Purple80
import com.example.koritsu.ui.theme.dark
import com.example.koritsu.ui.theme.darkGray
import com.example.koritsu.ui.theme.green
import com.example.koritsu.ui.theme.stroke
import com.example.koritsu.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    viewModel: CourseDetailViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    navController: NavController
) {
    val state: CourseDetailScreenState by remember { viewModel.state }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.preview),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(28.dp)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .background(Color.White, shape = CircleShape),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_icon),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    myIconButton { viewModel.onEvent(CourseDetailEvent.AddToWishList) }
                }
            }
        }
        Column(
            modifier = Modifier
                .background(dark)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement
                .spacedBy(20.dp)
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = state.course.title,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Purple80)
                )
                Column() {
                    Text(
                        text = "Автор",
                        style = MaterialTheme.typography.bodySmall,
                        color = stroke
                    )
                    Text(
                        text = "Merion Academy",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Column {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green,
                        contentColor = white
                    )
                ) {
                    Text(text = "Начать курс")
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = darkGray,
                        contentColor = white
                    )
                ) {
                    Text(text = "Перейти на платформу")
                }
            }
            Text(
                text = "О курсе",
                style = MaterialTheme.typography.titleLarge,
                color = white
            )
            Text(
                text = state.course.text,
                style = MaterialTheme.typography.bodyMedium,
                color = stroke
            )
        }
    }
}

@Composable
private fun myIconButton(function: () -> Unit) {
    IconButton(
        onClick = function,
        modifier = Modifier
            .size(28.dp)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(Color.White, shape = CircleShape),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.favorites_icon),
            contentDescription = null,
            tint = Color.Black
        )
    }
}