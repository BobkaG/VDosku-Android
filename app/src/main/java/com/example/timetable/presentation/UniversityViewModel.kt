package com.example.timetable.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.data.User
import com.example.timetable.data.Resource
import com.example.timetable.data.domain.use_case.GetUniversitiesUseCase
import com.example.timetable.data.domain.use_case.GetUniversityDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val getUniversitiesUseCase: GetUniversitiesUseCase,
    private val getUniversityDetailUseCase: GetUniversityDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel()
{
    private val _state = mutableStateOf(UniversityState())
    val state: State<UniversityState> = _state

    init {
        getUniversities(User.idUniversity)
    }

    private fun getUniversities(id: Long) {
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