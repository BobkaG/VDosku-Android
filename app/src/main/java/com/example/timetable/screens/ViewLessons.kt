package com.example.timetable.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.testproj.LessonCard
import com.example.timetable.LessonExample
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.Lesson
import com.example.timetable.presentation.TimetableViewModel
import com.mrerror.singleRowCalendar.SingleRowCalendar
import java.util.Date
import com.example.timetable.R
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600


@Composable
fun ViewLessons(
    viewModel: TimetableViewModel = hiltViewModel()
)
{
    val state = viewModel.state.value
    /*val lessons = mapOf(
        0 to mapOf(
            0 to listOf(
                LessonExample("12:00","21:00","Пн","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            1 to listOf(
                LessonExample("12:00","21:00","Вт","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            2 to listOf(
                LessonExample("12:00","21:00","Ср","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            3 to listOf(
                LessonExample("12:00","21:00","Чт","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            4 to listOf(
                LessonExample("12:00","21:00","Пт","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            5 to listOf(
                LessonExample("12:00","21:00","Сб","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            6 to listOf(
                LessonExample("12:00","21:00","Вскр","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),),
        1 to mapOf(
            0 to listOf(
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            1 to listOf(
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            2 to listOf(
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            3 to listOf(
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            4 to listOf(
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            5 to listOf(
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),
            6 to listOf(
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
                LessonExample("12:00","21:00","АИС","216*","Палехова О.И."),
            ),)
    )*/

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

            DayLessons(date = day.value, days = state.timetable)

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
fun DayLessons(date: Date, days: List<Day>) {
    val formatday : Int
    if (date.day == 0) formatday = 6
    else formatday = date.day - 1

    val filtered = days.filter { it.day == formatday}
    if (filtered.isNotEmpty()){
        val lessons = filtered.first().lessons
        LazyColumn {
            items(lessons){ lesson ->
                LessonCard(lesson)
            }
        }
    }
    else
    {
        Text(text = "Пар нет")
    }


/*    Column(modifier = Modifier.verticalScroll(state = ScrollState(1))) {
        for (lesson in lessons.get(day)) {
            LessonCard(lesson)
        }
    }*/


}