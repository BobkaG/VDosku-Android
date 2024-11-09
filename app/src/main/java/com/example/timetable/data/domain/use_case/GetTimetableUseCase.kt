package com.example.timetable.data.domain.use_case

import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.Timetable
import com.example.timetable.data.domain.repository.TimetableRepository
import com.example.timetable.data.remote.dto.toTimetable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTimetableUseCase @Inject constructor(
    private val repository: TimetableRepository
) {
    operator fun invoke(group: String): Flow<Resource<Timetable>> = flow {
        try {
            emit(Resource.Loading())
            val timetable = repository.getGroupTimetableByCode(group).toTimetable()
            emit(Resource.Success(timetable))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}