package com.example.timetable.presentation

import com.example.timetable.data.domain.model.Day


data class TimetableState(
    val isLoading: Boolean = false,
    val timetable: List<Day> = emptyList(),
    val error: String = ""
)