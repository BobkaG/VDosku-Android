package com.example.timetable.screens

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.timetable.R
import com.example.timetable.data.User
import com.example.timetable.data.domain.model.Day
import com.example.timetable.presentation.TimetableViewModel
import com.example.timetable.presentation.UniversityViewModel
import com.google.gson.Gson

fun saveDays(context: Context, days: List<Day>) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Сериализуем список в JSON строку
    val gson = Gson()
    val json = gson.toJson(days)

    editor.putString("days", json)
    editor.apply()
}

fun getUserData(context: Context): Map<String, Any?> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val group = sharedPreferences.getString("userGroup", "")
    val university = sharedPreferences.getString("nameUniversity", "")
    val isSigned = sharedPreferences.getBoolean("isSigned", false)
    return mapOf(
        "userGroup" to group,
        "nameUniversity" to university,
        "isSigned" to isSigned
    )
}

private fun initializeUserData(context: Context) {
    val userData = getUserData(context)
    User.userGroup = userData["userGroup"] as? String ?: ""
    User.nameUniversity = userData["nameUniversity"] as? String ?: ""
    User.isSigned.value = userData["isSigned"] as? Boolean ?: false
}

fun Logout(context: Context, group: String, university: String, isSigned: Boolean) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("userGroup", group)
    editor.putString("nameUniversity", university)
    editor.putBoolean("isSigned", isSigned)
    editor.apply()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ViewProfile(navController: NavController) {
    initializeUserData(navController.context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(painter = painterResource(id = R.drawable.bg), contentScale = ContentScale.FillBounds)
            .padding(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(painter = painterResource(R.drawable.person_fill_2), contentDescription = "", modifier = Modifier.padding(bottom = 32.dp))

        // Поле выбора ВУЗа с кастомным Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ВУЗ",
                    modifier = Modifier.weight(0.5f),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = User.nameUniversity,
                    modifier = Modifier.weight(1f),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Поле выбора группы с кастомным Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Группа",
                    modifier = Modifier.weight(0.5f),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = User.userGroup,
                    modifier = Modifier.weight(1f),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


        // Текст "Поддержка" снизу
        Column(
            modifier = Modifier.offset(y = 180.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = "Поддержка",
                fontSize = 14.sp,
                color = Color(0xFF8C2AC8), // Цвет текста для "Поддержка"
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "О нас",
                fontSize = 14.sp,
                color = Color(0xFF8C2AC8), // Цвет текста для "Поддержка"
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Выйти из профиля",
                fontSize = 14.sp,
                color = Color(0xFFC82A2A), // Цвет текста для "Поддержка"
                fontWeight = FontWeight.Normal,
                modifier = Modifier.clickable{
                    User.isSigned.value = false
                    Logout(context = navController.context,"","",false)
                    saveDays(context = navController.context,emptyList())
                }
            )
            Text(
                text = "Developed by IlyaBobkin@ 2024",
                fontSize = 14.sp,
                color = Color.Gray, // Цвет текста для "Поддержка"
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}
