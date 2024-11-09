package com.example.timetable.data.remote.dto

import com.example.timetable.data.domain.model.Timetable

class TimetableDTO : ArrayList<TimetableDTOItem>()

fun TimetableDTO.toTimetable(): Timetable
{
    return Timetable()
}