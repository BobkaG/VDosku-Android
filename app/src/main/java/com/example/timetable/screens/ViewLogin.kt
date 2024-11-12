package com.example.timetable.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.timetable.presentation.TimetableViewModel
import com.example.timetable.presentation.UniversityViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ViewLogin(navController: NavController,     viewModel: UniversityViewModel = hiltViewModel()) {
    var selectedUniversity by remember { mutableStateOf("БГТУ \"Военмех\"") }
    var selectedGroup by remember { mutableStateOf("О713Б") }
    val state = viewModel.state.value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(painter = painterResource(id = R.drawable.bg), contentScale = ContentScale.FillBounds)
            .padding(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var expandedUniversity by remember { mutableStateOf(false) }
        var expandedGroup by remember { mutableStateOf(false) }
        val universities = state.universities
        val groups = state.universityDetail?.groups // Пример списка групп

        // Заголовок
        Text(
            text = "Ваше расписание",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp).align(Alignment.Start),
        )
        Text(
            text = "Пожалуйста, выберите ВУЗ и группу",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp).align(Alignment.Start)
        )

        // Поле выбора ВУЗа с кастомным Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { if (!expandedGroup) expandedUniversity = !expandedUniversity }
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
                    text = selectedUniversity,
                    modifier = Modifier.weight(1f),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandedUniversity
                )
            }

            // Выпадающий список
            if (expandedUniversity) {
                DropdownMenu(
                    expanded = expandedUniversity,
                    onDismissRequest = {expandedUniversity = !expandedUniversity},
                    modifier = Modifier.fillMaxWidth(0.5f).padding(top = 8.dp)
                ) {
                    universities.forEach { university ->
                        DropdownMenuItem(
                            text = { Text(university.name) },
                            onClick = {
                                selectedUniversity = university.name
                                expandedUniversity = false
                            }
                        )
                    }
                }
            }
        }

        // Поле выбора группы с кастомным Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { if (!expandedUniversity) expandedGroup = !expandedGroup }
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
                    text = selectedGroup,
                    modifier = Modifier.weight(1f),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandedUniversity
                )
            }

            // Выпадающий список
            if (expandedGroup) {
                DropdownMenu(
                    onDismissRequest = {expandedGroup = !expandedGroup},
                    expanded = expandedGroup,
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    groups?.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(group) },
                            onClick = {
                                selectedGroup = group
                                expandedGroup = false
                            }
                        )
                    }
                }
            }
        }

        // Кнопка "Далее"
        Button(
            onClick = {
                User.isSigned.value = true
                User.userGroup = selectedGroup
                User.nameUniversity = state.universityDetail!!.name
                navController.navigate("home")
                      },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C2AC8) // цвет фона кнопки
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Далее",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        // Текст "Поддержка" снизу
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Нет ВУЗа или группы?",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Поддержка",
                fontSize = 14.sp,
                color = Color(0xFF8C2AC8), // Цвет текста для "Поддержка"
                fontWeight = FontWeight.Bold
            )
        }
    }
}
