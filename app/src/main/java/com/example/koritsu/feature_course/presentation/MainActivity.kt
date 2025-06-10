package com.example.koritsu.feature_course.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.koritsu.R
import com.example.koritsu.feature_course.data.remote.responses.Course
import com.example.koritsu.feature_course.presentation.account.AccountScreen
import com.example.koritsu.feature_course.presentation.authorization.AuthorizationScreen
import com.example.koritsu.feature_course.presentation.course_detail.CourseDetailEvent
import com.example.koritsu.feature_course.presentation.course_detail.CourseDetailScreen
import com.example.koritsu.feature_course.presentation.course_detail.CourseDetailViewModel
import com.example.koritsu.feature_course.presentation.courses.CoursesScreen
import com.example.koritsu.feature_course.presentation.courses_wishlist.CoursesWishlistScreen
import com.example.koritsu.ui.theme.KoritsuTheme
import com.example.koritsu.ui.theme.darkGray
import com.example.koritsu.ui.theme.dropShadow
import com.example.koritsu.ui.theme.green
import com.example.koritsu.ui.theme.lightGray
import com.example.koritsu.ui.theme.stroke
import com.example.koritsu.ui.theme.white
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoritsuTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AuthorizationScreenRoute
                    ) {
                        composable<AuthorizationScreenRoute> {
                            AuthorizationScreen(
                                innerPadding = innerPadding,
                                onNavigateToCoursesListScreen = {
                                    navController.navigate(
                                        Routes.ScreensWithBottomNavigation
                                    )
                                }
                            )
                        }
                        composable<Routes.ScreensWithBottomNavigation> {
                            val navController1 = rememberNavController()
                            Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                bottomBar = {
                                    BottomNavigationBar(
                                        items = listOf(
                                            BottomNavigationItem(
                                                name = "Главная",
                                                icon = painterResource(id = R.drawable.home_icon),
                                                route = Routes.CoursesListScreenRoute
                                            ),
                                            BottomNavigationItem(
                                                name = "Избранное",
                                                icon = painterResource(id = R.drawable.favorites_icon),
                                                route = Routes.CoursesWishListScreenRoute
                                            ),
                                            BottomNavigationItem(
                                                name = "Аккаунт",
                                                icon = painterResource(id = R.drawable.account_icon),
                                                route = Routes.AccountScreenRoute
                                            )
                                        ),
                                        navController = navController1,
                                        onItemClick = {
                                            navController1.navigate(it.route)
                                        }
                                    )

                                }
                            ) { innerPadding ->
                                Navigation(
                                    innerPadding = innerPadding,
                                    navController = navController1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


data class BottomNavigationItem(
    val name: String,
    val icon: Painter,
    val route: Routes
)

@Composable
fun Navigation(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.CoursesListScreenRoute
    ) {
        composable<Routes.CoursesListScreenRoute> {
            CoursesScreen(
                innerPadding = innerPadding,
                onNavigateToCourseDetailScreen = { course: Course ->
                    navController.navigate(
                        Routes.CourseDetailScreenRoute.toRoute(course)
                    )
                }
            )
        }
        composable<Routes.CoursesWishListScreenRoute> {
            CoursesWishlistScreen(
                innerPadding = innerPadding,
                onNavigateToCourseDetailScreen = { course: Course ->
                    navController.navigate(
                        Routes.CourseDetailScreenRoute.toRoute(course)
                    )
                }
            )
        }
        composable<Routes.AccountScreenRoute> {
            AccountScreen(
                innerPadding = innerPadding,
                onNavigateToCourseDetailScreen = { course: Course ->
                    navController.navigate(
                        Routes.CourseDetailScreenRoute.toRoute(course)
                    )
                }
            )
        }
        composable<Routes.CourseDetailScreenRoute> {
            val viewModel: CourseDetailViewModel = hiltViewModel()
            val course = it.toRoute<Routes.CourseDetailScreenRoute>().toCourse()
            viewModel.onEvent(CourseDetailEvent.SaveCurrentState(course))
            CourseDetailScreen(
                viewModel = viewModel,
                innerPadding = innerPadding,
                navController = navController
            )
        }
    }
}

@OptIn(InternalSerializationApi::class)
@ExperimentalMaterial3Api
@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavigationItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier
            .border(1.dp, color = stroke),
        containerColor = darkGray,

        ) {
        items.forEach { item ->
            val selected =
                item.route::class.serializer().descriptor.serialName == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemColors(
                    selectedIconColor = green,
                    unselectedIconColor = white,
                    selectedTextColor = green,
                    unselectedTextColor = white,
                    disabledIconColor = white,
                    disabledTextColor = white,
                    selectedIndicatorColor = lightGray
                ),
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            painter = item.icon,
                            contentDescription = item.name
                        )
                        Text(
                            text = item.name,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    }
}

@Serializable
object AuthorizationScreenRoute

@Serializable
sealed class Routes {

    @Serializable
    object CoursesListScreenRoute : Routes()

    @Serializable
    object CoursesWishListScreenRoute : Routes()

    @Serializable
    object AccountScreenRoute : Routes()

    @Serializable
    object ScreensWithBottomNavigation : Routes()

    @Serializable
    data class CourseDetailScreenRoute(
        val hasLike: Boolean,
        val id: Int,
        val price: String,
        val publishDate: String,
        val rate: String,
        val startDate: String,
        val text: String,
        val title: String

    ) : Routes() {
        companion object {
            fun toRoute(course: Course): CourseDetailScreenRoute {
                return CourseDetailScreenRoute(
                    hasLike = course.hasLike,
                    id = course.id,
                    price = course.price,
                    publishDate = course.publishDate,
                    rate = course.rate,
                    startDate = course.startDate,
                    text = course.text,
                    title = course.title
                )
            }
        }

        fun toCourse(): Course {
            return Course(
                hasLike = this.hasLike,
                id = this.id,
                price = this.price,
                publishDate = this.publishDate,
                rate = this.rate,
                startDate = this.rate,
                text = this.text,
                title = this.title
            )
        }
    }
}

