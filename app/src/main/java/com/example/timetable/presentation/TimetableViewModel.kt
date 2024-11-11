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
        getTimetable(User.userGroup, User.idUniversity)
        //savedStateHandle.get<String>(Constants.PARAM_GROUP)?.let{code -> getTimetable("О713Б")}
    }

    /*private fun getTimetable(code: String, id: Long)
    {
        val newstate = TimetableState()
        getUniversitiesUseCase().onEach { result ->
            when(result){
                is Resource.Success<List<University>> -> {
                    newstate.universities = result.data?:emptyList()
                }
                is Resource.Error<List<University>> -> {
                    newstate.error = result.message ?: "An unexpected error occured"
                }
                is Resource.Loading<List<University>> -> {
                    newstate.isLoading = true
                }
            }
        }.launchIn(viewModelScope)

        getTimetableUseCase(code).onEach { result ->
            when(result){
                is Resource.Success<List<Day>> -> {
                    newstate.timetable = result.data ?: emptyList()
                }
                is Resource.Error<List<Day>> -> {
                    newstate.error = result.message ?: "An unexpected error occured"
                }
                is Resource.Loading<List<Day>> -> {
                    newstate.isLoading = true
                }
            }
        }.launchIn(viewModelScope)

        getUniversityDetailUseCase(id).onEach { result ->
            when(result){
                is Resource.Success<UniversityDetail> -> {
                    newstate.universityDetail= result.data
                }
                is Resource.Error<UniversityDetail> -> {
                    newstate.error = result.message ?: "An unexpected error occured"
                }
                is Resource.Loading<UniversityDetail> -> {
                    newstate.isLoading = true
                }
            }
        }.launchIn(viewModelScope)

        _state.value = newstate
    }*/

    private fun getTimetable(code: String, id: Long) {
        // Устанавливаем статус загрузки вначале
        _state.value = _state.value.copy(isLoading = true)

        // Загружаем список университетов
        getUniversitiesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        universities = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

        // Загружаем расписание
        getTimetableUseCase(code).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        timetable = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

        // Загружаем детали университета
        getUniversityDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        universityDetail = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


}