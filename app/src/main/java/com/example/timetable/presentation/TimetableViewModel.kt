package com.example.timetable.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.data.User
import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.use_case.GetTimetableUseCase
import com.example.timetable.data.domain.use_case.GetUniversitiesUseCase
import com.example.timetable.data.domain.use_case.GetUniversityDetailUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date
import javax.inject.Inject

fun saveDays(context: Context, days: List<Day>) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Сериализуем список в JSON строку
    val gson = Gson()
    val json = gson.toJson(days)

    editor.putString("days", json)
    editor.apply()
}

// Функция для получения списка дней
fun getDays(context: Context): List<Day> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("days", null)

    // Если данные не найдены, возвращаем пустой список
    if (json.isNullOrEmpty()) {
        return emptyList()
    }

    // Десериализуем JSON строку в список объектов Day
    val gson = Gson()
    val type = object : TypeToken<List<Day>>() {}.type
    return gson.fromJson(json, type)
}

fun getUserData(context: Context): Map<String, Any?> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val group = sharedPreferences.getString("userGroup", "")
    val university = sharedPreferences.getString("nameUniversity", "")
    val isSigned = sharedPreferences.getBoolean("isSigned", false)
    return mapOf(
        "userGroup" to group,
        "universityName" to university,
        "isSigned" to isSigned
    )
}

private fun initializeUserData(context: Context) {
    val userData = getUserData(context)
    User.userGroup = userData["userGroup"] as? String ?: ""
    User.nameUniversity = userData["nameUniversity"] as? String ?: ""
    User.isSigned.value = userData["isSigned"] as? Boolean ?: false
}

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val getTimetableUseCase: GetTimetableUseCase,
    @ApplicationContext private val context: Context, // Контекст приложения
    private val savedStateHandle: SavedStateHandle
) : ViewModel()
{
    private val _selectedDay = savedStateHandle.getStateFlow("selectedDay", Date())
    val selectedDay: StateFlow<Date> get() = _selectedDay

    private val _state = mutableStateOf(TimetableState())
    val state: State<TimetableState> = _state

    fun setSelectedDay(day: Date) {
        savedStateHandle["selectedDay"] = day
    }

    init {
        initializeUserData(context = context)
        if (getDays(context).isEmpty())
            getTimetable(User.userGroup, User.idUniversity)
    }

    private fun getTimetable(code: String, id: Long) {
        // Устанавливаем статус загрузки вначале
        _state.value = _state.value.copy(isLoading = true)

        // Загружаем расписание
        getTimetableUseCase(code).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        timetable = result.data ?: emptyList(),
                        isLoading = false
                    )
                    saveDays(context, result.data ?: emptyList())
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