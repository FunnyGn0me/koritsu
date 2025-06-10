package com.example.koritsu.feature_course.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.koritsu.R
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.ui.theme.darkGray
import com.example.koritsu.ui.theme.glass
import com.example.koritsu.ui.theme.green
import com.example.koritsu.ui.theme.stroke
import com.example.koritsu.ui.theme.white

@Composable
fun CourseCard(
    course: Course,
    onNavigateToCourseScreen: (course: Course) -> Unit,
    function: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToCourseScreen(course) },
        colors = CardDefaults.cardColors(
            containerColor = darkGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomEnd = 15.dp,
                            bottomStart = 15.dp
                        )
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.preview),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = function,
                            modifier = Modifier
                                .wrapContentSize()
                                .background(glass, shape = CircleShape),
                        ) {
                            Icon(
                                painter = if (course.hasLike) painterResource(id = R.drawable.favourite_filled_icon) else painterResource(
                                    id = R.drawable.favorites_icon
                                ),
                                contentDescription = null,
                                tint = if (course.hasLike) green else white
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(glass, CircleShape)
                                .padding(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = green
                                )
                                Text(
                                    text = course.rate,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = white
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .background(glass, CircleShape)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = course.publishDate,
                                style = MaterialTheme.typography.bodyMedium,
                                color = white
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = white
                )
                Text(
                    text = course.text,
                    style = MaterialTheme.typography.bodySmall,
                    color = stroke,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${course.price} ₽",
                        style = MaterialTheme.typography.titleMedium,
                        color = white
                    )

                    Text(
                        text = "Подробнее →",
                        style = MaterialTheme.typography.titleSmall,
                        color = green,
                    )
                }
            }
        }
    }
}