package com.example.timetable.data.di

import com.example.timetable.data.Constants
import com.example.timetable.data.domain.repository.TimetableRepository
import com.example.timetable.data.remote.TimetableAPI
import com.example.timetable.data.repository.TimetableRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTimetableApi(): TimetableAPI
    {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TimetableAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTimetableRepository(api: TimetableAPI): TimetableRepository{
        return TimetableRepositoryImpl(api)
    }
}