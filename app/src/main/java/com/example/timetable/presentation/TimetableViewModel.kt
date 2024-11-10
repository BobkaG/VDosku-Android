package com.example.timetable.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.data.Group
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.AppDataStore
import com.example.timetable.User
import com.example.timetable.data.Constants
import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.use_case.GetTimetableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val getTimetableUseCase: GetTimetableUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel()
{
    private val _state = mutableStateOf(TimetableState())
    val state: State<TimetableState> = _state

    init {
        //savedStateHandle.get<String>(Constants.PARAM_GROUP)?.let{code -> getTimetable("О713Б")}
        getTimetable(User.userGroup)
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
}