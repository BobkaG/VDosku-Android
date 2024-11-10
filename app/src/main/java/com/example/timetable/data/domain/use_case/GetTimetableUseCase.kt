package com.example.timetable.data.domain.use_case

import android.util.Log
import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.repository.TimetableRepository
import com.example.timetable.data.remote.dto.DayDTO
import com.example.timetable.data.remote.dto.toDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTimetableUseCase @Inject constructor(
    private val repository: TimetableRepository
) {
    operator fun invoke(group: String): Flow<Resource<List<Day>>> = flow {
        try {
            emit(Resource.Loading())
            val timetable = repository.getGroupTimetableByCode(group).map { it.toDay() }
            emit(Resource.Success(timetable))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}