package com.example.timetable.data.domain.repository

import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.model.UniversityDetail
import com.example.timetable.data.remote.dto.DayDTO
import com.example.timetable.data.remote.dto.UniversityDTO
import com.example.timetable.data.remote.dto.UniversityDetailDTO

interface TimetableRepository {

    suspend fun getGroupTimetableByCode(code: String) : List<DayDTO>

    suspend fun getUniversities() : List<UniversityDTO>

    suspend fun getUniversityDetail(id: Long) : UniversityDetailDTO
}