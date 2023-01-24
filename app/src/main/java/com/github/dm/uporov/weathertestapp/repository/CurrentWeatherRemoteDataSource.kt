package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.api.WeatherApi
import com.github.dm.uporov.weathertestapp.api.model.CurrentWeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

private const val DEFAULT_LOCATION_LAT: Float = 56.84976f
private const val DEFAULT_LOCATION_LON: Float = 53.20448f

@Singleton
class CurrentWeatherRemoteDataSource @Inject constructor(
    private val api: WeatherApi
) {

    suspend fun getCurrentWeather(): CurrentWeatherResponse? {
        return api.getCurrentWeather(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LON).body()
    }
}