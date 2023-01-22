package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.api.WeatherApi
import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val REFRESH_INTERVAL_MS: Long = 15 * 60 * 1000 // 15 minutes
private const val DEFAULT_LOCATION_LAT: Float = 56.84976f
private const val DEFAULT_LOCATION_LON: Float = 53.20448f

@ViewModelScoped
class ForecastRemoteDataSource @Inject constructor(
    private val api: WeatherApi
) {

    val latestForecast: Flow<ForecastResponse?> = flow {
        while (true) {
            val response = api.getForecast(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LON)
            emit(response.body())
            delay(REFRESH_INTERVAL_MS)
        }
    }
}