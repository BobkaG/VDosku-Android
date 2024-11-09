package com.example.timetable

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.timetable.navigation.BottomItem
import com.example.timetable.screens.ViewLessons
import com.example.timetable.screens.ViewLogin
import com.example.timetable.screens.ViewProfile
import com.example.timetable.ui.theme.TimetableTheme
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


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
    val context = LocalContext.current
    val ads = AppDataStore(context)

/*    LaunchedEffect(key1 = true) {
        ads.registerUniversity("БГТУ «ВОЕНМЕХ»")
        val univ = ads.readUniversity()
        ads.registerGroup("О713Б")
        val group = ads.readGroup()
        Log.e("university = ",univ)
        Log.e("group = ",group)
    }*/


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
        NavHost(navController = navController, startDestination = "home")
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
                ViewLogin()
            }
        }
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimetableTheme {
        Greeting("Android")
    }
}