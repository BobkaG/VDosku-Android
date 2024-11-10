package com.example.timetable.presentation

import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.model.UniversityDetail


data class TimetableState(
    val isLoading: Boolean = false,
    val timetable: List<Day> = emptyList(),
    var universities: List<University> = emptyList(),
    var universityDetail: UniversityDetail? = null,
    val error: String = ""
)