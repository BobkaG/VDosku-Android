package com.example.timetable.data.domain.use_case

import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.model.UniversityDetail
import com.example.timetable.data.domain.repository.TimetableRepository
import com.example.timetable.data.remote.dto.toUniversity
import com.example.timetable.data.remote.dto.toUniversityDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUniversityDetailUseCase @Inject constructor(
    private val repository: TimetableRepository
) {
    operator fun invoke(id: Long): Flow<Resource<UniversityDetail>> = flow {
        try {
            emit(Resource.Loading())
            val universityDetail = repository.getUniversityDetail(id).toUniversityDetail()
            emit(Resource.Success(universityDetail))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error("Не удалось связаться с сервером. Проверьте свое интернет-соединение"))
        }
    }
    }