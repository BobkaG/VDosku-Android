package com.mrerror.singleRowCalendar

import android.annotation.SuppressLint
import android.widget.DatePicker
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.timetable.R
import com.mrerror.singleRowCalendar.DateUtils.getFutureDates
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue601
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Grey500
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    selectedDate: Date
) {
    // Синхронизируем начальное состояние DatePicker с выбранной датой
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.time)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = Blue600,
                todayContentColor = Blue600,
                todayDateBorderColor = Blue600
            )
        )
    }
}

@Composable
fun SingleRowCalendar(
    modifier: Modifier = Modifier,
    selectedDayBackgroundColor: Color = Blue600,
    selectedDayTextColor: Color = Color.White,
    dayNumTextColor: Color = Color.Black,
    dayTextColor: Color = Grey500,
    iconsTintColor: Color = Color.Black,
    headTextColor: Color = Color.Black,
    headTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    @DrawableRes nextDrawableRes: Int = R.drawable.baseline_keyboard_double_arrow_right_24,
    @DrawableRes prevDrawableRes: Int = R.drawable.baseline_keyboard_double_arrow_left_24,
    onSelectedDayChange: (Date) -> Unit,
) {
    val calendar = Calendar.getInstance(Locale.getDefault())
    var selectedDate by rememberSaveable { mutableStateOf(calendar.time) }
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Инициализируем с понедельника
    var currentDate by rememberSaveable { mutableStateOf(calendar.time) }

    Column(modifier) {
        WeekHeader(
            firstDayDate = currentDate,
            iconsTintColor = iconsTintColor,
            headTextColor = headTextColor,
            headTextStyle = headTextStyle,
            nextDrawableRes = nextDrawableRes,
            prevDrawableRes = prevDrawableRes,
            onNextWeekClicked = {
                calendar.time = it
                currentDate = calendar.time
                selectedDate = currentDate
                onSelectedDayChange(selectedDate)
            },
            onPrevWeekClicked = {
                calendar.time = it
                currentDate = calendar.time
                selectedDate = currentDate
                onSelectedDayChange(selectedDate)
            },
            selectedDate = selectedDate,
            onSelectDay = { day ->
                selectedDate = day
                calendar.time = day
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Устанавливаем текущую дату на понедельник
                currentDate = calendar.time
                onSelectedDayChange(selectedDate)
            }
        )

        WeekDaysHeader(
            selectedDayBackgroundColor = selectedDayBackgroundColor,
            selectedDayTextColor = selectedDayTextColor,
            dayNumTextColor = dayNumTextColor,
            dayTextColor = dayTextColor,
            firstDayDate = currentDate,
            selectedDate = selectedDate,
            onSelectDay = { day ->
                calendar.time = day
                selectedDate = day
                onSelectedDayChange(day)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekHeader(
    modifier: Modifier = Modifier,
    iconsTintColor: Color,
    headTextColor: Color,
    headTextStyle: TextStyle,
    firstDayDate: Date,
    @DrawableRes nextDrawableRes: Int,
    @DrawableRes prevDrawableRes: Int,
    onNextWeekClicked: (firstDayDate: Date) -> Unit,
    onPrevWeekClicked: (firstDayDate: Date) -> Unit,
    onSelectDay: (Date) -> Unit,
    selectedDate: Date,
) {

    val dayName = DateUtils.getDayNumber(selectedDate)
    val monthName = DateUtils.getMonthName(selectedDate)
    val yearName = DateUtils.getYear(selectedDate)
    val weekFinalDays = getFutureDates(6, Calendar.getInstance().apply { time = firstDayDate })
    val weekFinalDate = weekFinalDays.last()
    val fDayName = DateUtils.getDayNumber(weekFinalDate)
    val fMonthName = DateUtils.getMonthName(weekFinalDate)
    val fYearName = DateUtils.getYear2Letters(weekFinalDate)
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        Popup(
            onDismissRequest = { showDatePicker = false },
            alignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(350.dp)
                    .shadow(elevation = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
            ) {
                DatePickerModal(
                    selectedDate = selectedDate,  // Передаем выбранную дату для синхронизации
                    onDateSelected = {
                        it?.let { selectedMillis ->
                            val selected = Date(selectedMillis)
                            onSelectDay(selected)  // Обновляем выбранную дату
                        }
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    }

    Row(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.clickable {
                val c = Calendar.getInstance()
                c.time = firstDayDate
                c.add(Calendar.DATE, -7)
                val prevWeekFirstDay = c.time
                onPrevWeekClicked(prevWeekFirstDay)
            },
            painter = painterResource(id = prevDrawableRes),
            contentDescription = "",
            colorFilter = ColorFilter.tint(iconsTintColor)
        )

        Column(
            modifier = Modifier
                .width(150.dp)
                .height(35.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Blue601)
                .clickable {
                    showDatePicker = !showDatePicker
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(selectedDate),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 16.sp,
                color = headTextColor,
            )
        }

        Image(
            modifier = Modifier.clickable {
                val c = Calendar.getInstance()
                c.time = firstDayDate
                c.add(Calendar.DATE, 7)
                val nextWeekFirstDay = c.time
                onNextWeekClicked(nextWeekFirstDay)
            },
            painter = painterResource(id = nextDrawableRes),
            contentDescription = "",
            colorFilter = ColorFilter.tint(iconsTintColor)
        )
    }
}


@Composable
fun WeekDaysHeader(
    modifier: Modifier = Modifier,
    selectedDayBackgroundColor: Color,
    selectedDayTextColor: Color,
    dayNumTextColor: Color,
    dayTextColor: Color,
    firstDayDate: Date,
    selectedDate: Date,
    onSelectDay: (Date) -> Unit
) {

    val weekFinalDays = getFutureDates(6, Calendar.getInstance().apply { time = firstDayDate })
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        for (day in weekFinalDays) {
            Column(
                modifier = (if (selectedDate == day) Modifier
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Blue600)
                else Modifier
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.White)
                        )
                    .weight(1f)
                    .height(50.dp)
                    .width(50.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onSelectDay(day)
                    }


                , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Text(text = DateUtils.getDay3LettersName(day),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    color = if (selectedDate == day) selectedDayTextColor else dayNumTextColor,
                    /*modifier = (if (selectedDate == day) Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawRoundRect(
                                color = selectedDayBackgroundColor,
                                cornerRadius = CornerRadius(50.0f,50.0f),
                                size =  Size(this.size.width,this.size.width)
                            )
                        }
                        .padding(top = 12.dp)

                    else Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawRoundRect(
                                color = selectedDayTextColor,
                                cornerRadius = CornerRadius(50.0f,50.0f),
                                size =  Size(this.size.width,this.size.width)
                            )
                        }
                        .padding(top = 12.dp)
                        )
                        .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onSelectDay(day)
                    }*/
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

object SingleRowCalendarDefaults {
    val Blue600: Color = Color(0xFF8C2AC8)
    val Blue601: Color = Color(0x79CA93FF)
    val Grey500: Color = Color(0xFF667085)
}

@Preview
@Composable
fun SingleRowCalendarPreview() {
    SingleRowCalendar() {}
}

@Preview(locale = "ar")
@Composable
fun SingleRowCalendarARPreview() {
    SingleRowCalendar(modifier = Modifier.fillMaxWidth()) {}
}