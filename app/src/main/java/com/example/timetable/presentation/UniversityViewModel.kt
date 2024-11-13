package com.example.timetable.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.data.User
import com.example.timetable.data.Resource
import com.example.timetable.data.domain.model.Day
import com.example.timetable.data.domain.model.University
import com.example.timetable.data.domain.use_case.GetUniversitiesUseCase
import com.example.timetable.data.domain.use_case.GetUniversityDetailUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val getUniversitiesUseCase: GetUniversitiesUseCase,
    private val getUniversityDetailUseCase: GetUniversityDetailUseCase,
    @ApplicationContext private val context: Context // Контекст приложения
) : ViewModel()
{

    fun saveUniversities(context: Context, universities: List<University>) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Сериализуем список в JSON строку
        val gson = Gson()
        val json = gson.toJson(universities)

        editor.putString("universities", json)
        editor.apply()
    }

    // Функция для получения списка дней
    fun getLocalUniversities(context: Context): List<University> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("universities", null)

        // Если данные не найдены, возвращаем пустой список
        if (json.isNullOrEmpty()) {
            return emptyList()
        }

        // Десериализуем JSON строку в список объектов Day
        val gson = Gson()
        val type = object : TypeToken<List<University>>() {}.type
        return gson.fromJson(json, type)
    }

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
                    saveUniversities(context = context, result.data ?: emptyList())
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
