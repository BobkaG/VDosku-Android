package com.example.timetable

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimetableApplication : Application()
{
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "universities_cache")
}