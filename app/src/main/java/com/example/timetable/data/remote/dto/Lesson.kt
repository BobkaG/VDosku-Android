package com.example.timetable.data.remote.dto

data class Lesson(
    val audience: String,
    val end: String,
    val id: Int,
    val order: Int,
    val start: String,
    val teacher: String,
    val title: String
)