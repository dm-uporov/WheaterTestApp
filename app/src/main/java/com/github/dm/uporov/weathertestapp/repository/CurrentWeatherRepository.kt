package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.api.model.CurrentWeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

interface CurrentWeatherRepository {
    suspend fun getCurrentWeather(): CurrentWeatherResponse?
}

@Singleton
class CurrentWeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: CurrentWeatherRemoteDataSource,
) : CurrentWeatherRepository {

    override suspend fun getCurrentWeather(): CurrentWeatherResponse? {
        return remoteDataSource.getCurrentWeather()
    }
}