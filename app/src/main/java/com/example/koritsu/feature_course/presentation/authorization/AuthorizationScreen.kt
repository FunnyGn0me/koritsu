package com.example.koritsu.feature_course.presentation.authorization

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.example.koritsu.R
import com.example.koritsu.ui.theme.dark
import com.example.koritsu.ui.theme.darkGray
import com.example.koritsu.ui.theme.green
import com.example.koritsu.ui.theme.lightGray
import com.example.koritsu.ui.theme.okEnd
import com.example.koritsu.ui.theme.okStart
import com.example.koritsu.ui.theme.stroke
import com.example.koritsu.ui.theme.vk
import com.example.koritsu.ui.theme.white

@Composable
fun AuthorizationScreen(
    innerPadding: PaddingValues,
    onNavigateToCoursesListScreen: (userName: String) -> Unit
) {
    var login: String = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(dark)
            .padding(16.dp),
        verticalArrangement = Arrangement
            .Center
    ) {
        Text(
            text = "Вход",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)

        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                text = "Email",
                color = white,
                style = MaterialTheme.typography.bodyMedium
            )
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = { Text("example@gmail.com") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
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
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "Пароль",
                    color = white,
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = { Text("Введите пароль") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
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
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onNavigateToCoursesListScreen(login) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = green,
                contentColor = white
            )
        ) {
            Text(
                text = "Вход",
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Нету аккаунта?",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.bodySmall,
                color = green
            )
        }
        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Забыл пароль",
                style = MaterialTheme.typography.bodySmall,
                color = green
            )
        }
        Spacer(Modifier.height(25.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = stroke
        ) { }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)
        ) {
            SocialIconButton(
                icon = painterResource(id = R.drawable.vk_icon),
                url = "https://vk.com/",
                colorStart = vk,
                colorEnd = vk
            )
            SocialIconButton(
                icon = painterResource(id = R.drawable.ok_icon),
                url = "https://ok.ru/",
                colorStart = okStart,
                colorEnd = okEnd
            )
        }
    }
}

@Composable
fun SocialIconButton(icon: Painter, url: String, colorStart: Color, colorEnd: Color) {
    val context = LocalContext.current

    IconButton(
        modifier = Modifier
            .padding(vertical = 25.dp)
            .width(164.dp)
            .background(
                brush = Brush.verticalGradient(
                    (listOf(colorStart, colorEnd))
                ),
                shape = CircleShape
            ),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },) {
        Icon(painter = icon, contentDescription = null, tint = white)
    }
}