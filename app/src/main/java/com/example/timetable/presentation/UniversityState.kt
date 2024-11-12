package com.example.timetable.presentation

import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.model.UniversityDetail

data class UniversityState(
    var isLoading: Boolean = false,
    var universities: List<University> = emptyList(),
    var universityDetail: UniversityDetail? = null,
    var error: String = ""
)