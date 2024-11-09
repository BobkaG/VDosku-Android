package com.example.timetable.data.remote.dto

data class TimetableDTOItem(
    val day: Int,
    val lessons: List<Lesson>,
    val week: Int
)