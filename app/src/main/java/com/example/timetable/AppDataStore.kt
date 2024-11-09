package com.example.timetable

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class AppDataStore(var context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("information")

    companion object
    {
        val UNIV_KEY = stringPreferencesKey("AD")
        val GROUP_KEY = stringPreferencesKey("AD")
    }


    suspend fun registerUniversity(university: String)
    {
        context.dataStore.edit {
            it[UNIV_KEY] = university
        }
    }

    suspend fun readUniversity(): String
    {
        val p = context.dataStore.data.first()
        return p[UNIV_KEY]?:"no find university"
    }

    suspend fun registerGroup(group: String)
    {
        context.dataStore.edit {
            it[GROUP_KEY] = group
        }
    }

    suspend fun readGroup(): String
    {
        val p = context.dataStore.data.first()
        return p[GROUP_KEY]?:"no find group"
    }
}