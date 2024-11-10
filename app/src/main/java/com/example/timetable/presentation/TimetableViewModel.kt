package com.example.timetable.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.User
import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.model.UniversityDetail
import com.example.timetable.data.domain.use_case.GetTimetableUseCase
import com.example.timetable.data.domain.use_case.GetUniversitiesUseCase
import com.example.timetable.data.domain.use_case.GetUniversityDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val getTimetableUseCase: GetTimetableUseCase,
    private val getUniversitiesUseCase: GetUniversitiesUseCase,
    private val getUniversityDetailUseCase: GetUniversityDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel()
{
    private val _state = mutableStateOf(TimetableState())
    val state: State<TimetableState> = _state

    init {
        getTimetable(User.userGroup)
        getUnivesities()
        getUnivesityDetail(User.idUniversity)
        //savedStateHandle.get<String>(Constants.PARAM_GROUP)?.let{code -> getTimetable("О713Б")}
    }

    private fun getTimetable(code: String)
    {
        getTimetableUseCase(code).onEach { result ->
            when(result){
                is Resource.Success<List<Day>> -> {
                    _state.value = TimetableState(timetable = result.data ?: emptyList())
                }
                is Resource.Error<List<Day>> -> {
                    _state.value = TimetableState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading<List<Day>> -> {
                    _state.value = TimetableState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getUnivesities()
    {
        getUniversitiesUseCase().onEach { result ->
            when(result){
                is Resource.Success<List<University>> -> {
                    _state.value.universities= result.data?:emptyList()
                }
                is Resource.Error<List<University>> -> {
                    _state.value = TimetableState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading<List<University>> -> {
                    _state.value = TimetableState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getUnivesityDetail(id: Long)
    {
        getUniversityDetailUseCase(id).onEach { result ->
            when(result){
                is Resource.Success<UniversityDetail> -> {
                    _state.value.universityDetail= result.data
                }
                is Resource.Error<UniversityDetail> -> {
                    _state.value = TimetableState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading<UniversityDetail> -> {
                    _state.value = TimetableState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}