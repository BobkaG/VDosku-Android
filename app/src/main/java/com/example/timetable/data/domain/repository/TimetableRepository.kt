package com.example.timetable.data.domain.repository

import com.example.timetable.data.remote.dto.DayDTO

interface TimetableRepository {

    suspend fun getGroupTimetableByCode(code: String) : List<DayDTO>
}