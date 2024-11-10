package com.example.timetable.data.remote.dto

import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.model.UniversityDetail

data class UniversityDetailDTO(
    val groups: List<String>,
    val id: Long,
    val name: String
)

fun UniversityDetailDTO.toUniversityDetail(): UniversityDetail
{
    return UniversityDetail(
        id = id,
        name = name,
        groups = groups
    )
}