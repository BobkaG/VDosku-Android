package com.example.timetable.data.repository

import com.example.timetable.data.domain.repository.TimetableRepository
import com.example.timetable.data.remote.TimetableAPI
import com.example.timetable.data.remote.dto.TimetableDTO
import javax.inject.Inject


class TimetableRepositoryImpl @Inject constructor(
    private val api: TimetableAPI
) : TimetableRepository {

    override suspend fun getGroupTimetableByCode(code: String): TimetableDTO {
        return api.getGroupTimetableByCode(code)
    }
}
