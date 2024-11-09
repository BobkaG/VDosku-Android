package com.example.timetable.data.domain.model

data class TimetableItem(
    val day: Int,
    val lessons: List<Lesson>,
    val week: Int
)