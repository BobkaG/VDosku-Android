package com.example.timetable.presentation

import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.model.UniversityDetail


data class TimetableState(
    var isLoading: Boolean = false,
    var timetable: List<Day> = emptyList(),
    var universities: List<University> = emptyList(),
    var universityDetail: UniversityDetail? = null,
    var error: String = ""
)