package com.example.timetable.data.remote.dto

import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.Lesson

data class DayDTO(
    val day: Int,
    val lessons: List<Lesson>,
    val week: Int
)

fun DayDTO.toDay(): Day
{
    return Day(
        day = day,
        lessons = lessons,
        week = week
    )
}