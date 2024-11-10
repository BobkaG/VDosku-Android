package com.example.timetable.data.domain.use_case


import android.util.Log
import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.repository.TimetableRepository
import com.example.timetable.data.remote.dto.DayDTO
import com.example.timetable.data.remote.dto.toUniversity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUniversitiesUseCase @Inject constructor(
    private val repository: TimetableRepository
) {
    operator fun invoke(): Flow<Resource<List<University>>> = flow {
        try {
            emit(Resource.Loading())
            val universities = repository.getUniversities().map { it.toUniversity() }
            emit(Resource.Success(universities))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error("Не удалось связаться с сервером. Проверьте свое интернет-соединение"))
        }
    }
}