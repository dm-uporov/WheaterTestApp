package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.api.WeatherApi
import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val REFRESH_INTERVAL_MS: Long = 15 * 60 * 1000 // 15 minutes
private const val DEFAULT_LOCATION: String = "London"

class ForecastRemoteDataSource @Inject constructor(
    private val api: WeatherApi
) {

    var location: String = DEFAULT_LOCATION

    val latestForecast: Flow<ForecastResponse?> = flow {
        while (true) {
            val response = api.getForecast(location)
            emit(response.body())
            delay(REFRESH_INTERVAL_MS)
        }
    }
}