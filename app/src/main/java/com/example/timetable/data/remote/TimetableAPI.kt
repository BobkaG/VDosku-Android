package com.example.timetable.data.remote

import retrofit2.http.GET

interface TimetableAPI {

    @GET("/api/v1/universities/group_timetable/{code}")
    suspend fun getGroupTimetable()
}