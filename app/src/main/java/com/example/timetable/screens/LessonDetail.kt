package com.example.timetable.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timetable.DateLesson
import com.example.timetable.data.domain.model.Lesson
import com.example.timetable.R
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue601
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LessonDetail(navController: NavController, dateLesson: DateLesson) {

    val lessonType = when {
        dateLesson.lesson.title.startsWith("пр ") -> "Практика"
        dateLesson.lesson.title.startsWith("лек ") -> "Лекция"
        else -> "-"
    }

    val formattedDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(dateLesson.date)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TopAppBar с кнопкой назад
        TopAppBar(
            title = {
                Text(
                    text = "Информация о занятии",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = Blue600
                    )
                }
            },
            backgroundColor = Color.White,
            contentColor = Blue600,
            elevation = 4.dp
        )

        // Карточка с информацией
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(10.dp,RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .border(1.dp,Blue601,RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetailRow(label = "Дата", value = formattedDate)
                DetailRow(label = "Тип занятия", value = lessonType)
                DetailRow(
                    label = "Предмет",
                    value = dateLesson.lesson.title.removePrefix("пр ").removePrefix("лек ")
                )
                DetailRow(
                    label = "Преподаватель",
                    value = if (dateLesson.lesson.teacher.isNullOrBlank()) "-" else dateLesson.lesson.teacher
                )
                DetailRow(
                    label = "Аудитория",
                    value = if (dateLesson.lesson.audience.isNullOrBlank()) "-" else dateLesson.lesson.audience.dropLast(1)
                )
                DetailRow(
                    label = "Время",
                    value = "${dateLesson.lesson.start.dropLast(3)} - ${dateLesson.lesson.end.dropLast(3)}"
                )
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Blue600
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black)
    }
}
