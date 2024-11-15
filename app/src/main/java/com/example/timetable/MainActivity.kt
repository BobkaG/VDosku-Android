package com.example.timetable

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.timetable.data.User
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.Lesson
import com.example.timetable.navigation.BottomItem
import com.example.timetable.screens.LessonDetail
import com.example.timetable.screens.ViewLessons
import com.example.timetable.screens.ViewLogin
import com.example.timetable.screens.ViewProfile
import com.example.timetable.ui.theme.TimetableTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

class DateLesson(
    val lesson: Lesson,
    val date: Date,
    val note : String = ""
)

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

fun saveLesson(context: Context, lesson: DateLesson) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(lesson)
    editor.putString("lesson", json)
    editor.apply()
}

fun getLesson(context: Context): DateLesson? {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("lesson", null) ?: return null
    val gson = Gson()
    val type = object : TypeToken<DateLesson>() {}.type
    return gson.fromJson(json, type)
}

fun getUserData(context: Context): Map<String, Any?> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val group = sharedPreferences.getString("userGroup", "")
    val university = sharedPreferences.getString("nameUniversity", "")
    val isSigned = sharedPreferences.getBoolean("isSigned", false)
    return mapOf(
        "userGroup" to group,
        "universityName" to university,
        "isSigned" to isSigned
    )
}

private fun initializeUserData(context: Context) {
    val userData = getUserData(context)
    User.userGroup = userData["userGroup"] as? String ?: ""
    User.nameUniversity = userData["nameUniversity"] as? String ?: ""
    User.isSigned.value = userData["isSigned"] as? Boolean ?: false
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Show() {
    initializeUserData(LocalContext.current)
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
                ViewLessons(navController = navController, onLessonClick = { lesson, date ->
                    saveLesson(context = navController.context, DateLesson(lesson,date))
                    navController.navigate("lessonDetails/${lesson.id}&${date}")
                })
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
            composable(
                "lessonDetails/{lessonId}",
                arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
            )
            {
                backStackEntry -> // Загрузите информацию о занятии по lessonId
                val lesson = getLesson(navController.context) // Реализуйте эту функцию
                if (lesson != null) {
                    LessonDetail(navController, lesson)
                } else {
                    Text("Занятие не найдено")
                }
            }
        }
    }
}