package com.example.timetable.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.testproj.LessonCard
import com.example.timetable.data.domain.model.Day
import com.example.timetable.presentation.TimetableViewModel
import com.mrerror.singleRowCalendar.SingleRowCalendar
import java.util.Date
import com.example.timetable.R
import com.example.timetable.User
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600
import java.text.SimpleDateFormat
import java.util.Locale

class Week(var parity : Int, var number : Int)

fun calculateWeekParity(startWeek: String, currentDate: Date): Week {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val startDate = dateFormat.parse(startWeek) ?: return Week(0,0)

    val diffInMillis = currentDate.time - startDate.time
    val weeksBetween = (diffInMillis / (7 * 24 * 60 * 60 * 1000)).toInt()

    return if (weeksBetween % 2 == 0) Week(0,weeksBetween) else Week(1,weeksBetween)
}

fun getDayIndex(date: Date): Int {
    return if (date.day == 0) 6 else date.day - 1
}

@Composable
fun ViewLessons(
    viewModel: TimetableViewModel = hiltViewModel()
)
{
    val state = viewModel.state.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val day = remember { mutableStateOf(Date()) }

        if (viewModel.state.value.timetable.isNotEmpty()) {
            SingleRowCalendar(
                onSelectedDayChange = { selectedDate -> // верхний календарь
                    day.value = selectedDate
                },
                selectedDayBackgroundColor = Color(0xFF8C2AC8)
            )

            val filtered = state.universities.filter { it.id == User.idUniversity}
            if (filtered.isNotEmpty()){
                val start_week = filtered.first().start_week
                DayLessons(date = day.value, days = state.timetable, start_week = start_week)
            }
            else
            {
                CircularProgressIndicator(color = Blue600,modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 400.dp, start = 20.dp, end = 20.dp))
            }

        }
        else if (viewModel.state.value.isLoading)
            CircularProgressIndicator(color = Blue600,modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 400.dp, start = 20.dp, end = 20.dp))
        else {
            val context = LocalContext.current
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    add(ImageDecoderDecoder.Factory())
                }
                .build()
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context).data(data = R.drawable.nointernet).build(), imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().padding(top = 350.dp).size(70.dp) // Устанавливает размер изображения

            )
            Text(
                viewModel.state.value.error,
                modifier = Modifier.padding(top = 10.dp,start = 20.dp, end = 20.dp)
            )
        }
    }

    }




@Composable
fun DayLessons(date: Date, days: List<Day>, start_week: String) {
    val dayIndex = getDayIndex(date) // Correct day index, where Monday = 0
    var week = calculateWeekParity(start_week, date) // Determine week parity

    if (dayIndex == 0) {
        if (week.parity > 0) week.parity = week.parity - 1 else week.parity = week.parity + 1
        week.number += 4
    }
    else week.number += 3

    Text(text = "${week.number}-я неделя, " + if (week.parity > 0) "четная" else "нечетная", modifier = Modifier.fillMaxWidth().offset(y = -92.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.titleSmall, color = if (week.number == 5 || week.number == 10 || week.number == 15) Color.Red else Color.Gray)

    // Filter lessons for the current day and week parity
    val filtered = days.filter { it.day == dayIndex && it.week == week.parity}

    if (filtered.isNotEmpty()) {
        val lessons = filtered.first().lessons
        LazyColumn {
            items(lessons) { lesson ->
                LessonCard(lesson)
            }
        }
    } else {
        Text(text = "Пар нет")
    }
}
