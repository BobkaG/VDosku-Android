package com.example.timetable.data.remote

import com.example.timetable.data.domain.model.University
import com.example.timetable.data.remote.dto.DayDTO
import com.example.timetable.data.remote.dto.UniversityDTO
import com.example.timetable.data.remote.dto.UniversityDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface TimetableAPI {

    @GET("/api/v1/universities/group_timetable/{code}")
    suspend fun getGroupTimetableByCode(@Path("code") code: String) : List<DayDTO>

    @GET("/api/v1/universities/")
    suspend fun getUniversities() : List<UniversityDTO>

    @GET("/api/v1/universities/university/{id}")
    suspend fun getUniversityDetail(@Path("id") id: Long) : UniversityDetailDTO
}