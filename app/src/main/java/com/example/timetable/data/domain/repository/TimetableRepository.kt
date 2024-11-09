package com.example.timetable.data.domain.repository

import com.example.timetable.data.remote.dto.TimetableDTO

interface TimetableRepository {

    suspend fun getGroupTimetableByCode(code: String) : TimetableDTO
}