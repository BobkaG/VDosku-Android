package com.example.timetable.data.remote.dto

data class LessonDTO(
    val audience: String,
    val end: String,
    val id: Int,
    val order: Int,
    val start: String,
    val teacher: String,
    val title: String
)