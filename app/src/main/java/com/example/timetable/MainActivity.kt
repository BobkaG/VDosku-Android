package com.example.timetable

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.timetable.data.User
import com.example.timetable.navigation.BottomItem
import com.example.timetable.screens.ViewLessons
import com.example.timetable.screens.ViewLogin
import com.example.timetable.screens.ViewProfile
import com.example.timetable.ui.theme.TimetableTheme
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimetableTheme {
                Show()
        }
    }
}}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Show() {

    val navController = rememberNavController()
    val listItems = listOf(
        BottomItem.HomeScreen,
        BottomItem.SearchScreen,
        BottomItem.ProfileScreen
    )
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar =
        {
            if (User.isSigned.value)
            NavigationBar(
                containerColor = Color.Transparent
            ) {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                listItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = "Icon"
                            )
                        },
                        colors = NavigationBarItemColors(
                            selectedIconColor = Blue600, unselectedIconColor = Color.Gray,
                            selectedTextColor = Color.Black,
                            selectedIndicatorColor = Color.Transparent,
                            unselectedTextColor = Color.Gray,
                            disabledIconColor = Color.Gray,
                            disabledTextColor = Color.Gray
                        )
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize().paint(painter = painterResource(id = R.drawable.bg), contentScale = ContentScale.FillBounds)
    )
    {
        NavHost(navController = navController, startDestination = if (User.isSigned.value) "home" else "login")
        {
            composable("home")
            {
                ViewLessons()
            }
            composable("search")
            {
                Text("Search", Modifier.fillMaxSize(), textAlign = TextAlign.Center)
            }
            composable("profile")
            {
                ViewProfile(navController)
            }
            composable("login")
            {
                ViewLogin(navController)
            }
        }
    }
}