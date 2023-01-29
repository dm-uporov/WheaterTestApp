package com.github.dm.uporov.weathertestapp.api.datasource

import com.github.dm.uporov.weathertestapp.api.WeatherApi
import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ForecastRemoteDataSource @Inject constructor(
    private val api: WeatherApi
) {

    suspend fun getForecast(latitude: Double, longitude: Double): ForecastResponse? {
        return api.getForecast(latitude, longitude).body()
    }
}