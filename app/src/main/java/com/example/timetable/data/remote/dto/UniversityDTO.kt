package com.example.timetable.data.remote.dto

import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.Lesson
import com.example.timetable.data.domain.model.University

data class UniversityDTO(
    val id: Long,
    val name: String,
    val start_week: String,
)

fun UniversityDTO.toUniversity(): University
{
    return University(
        id = id,
        name = name,
        start_week = start_week
    )
}