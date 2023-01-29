package com.github.dm.uporov.weathertestapp.api.datasource

import com.github.dm.uporov.weathertestapp.api.WeatherApi
import com.github.dm.uporov.weathertestapp.api.model.CurrentWeatherResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CurrentWeatherRemoteDataSource @Inject constructor(
    private val api: WeatherApi
) {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherResponse? {
        return api.getCurrentWeather(latitude, longitude).body()
    }
}