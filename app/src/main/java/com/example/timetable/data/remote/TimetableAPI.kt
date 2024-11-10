package com.example.timetable.data.remote

import com.example.timetable.data.remote.dto.DayDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface TimetableAPI {

    @GET("/api/v1/universities/group_timetable/{code}")
    suspend fun getGroupTimetableByCode(@Path("code") code: String) : List<DayDTO>
}