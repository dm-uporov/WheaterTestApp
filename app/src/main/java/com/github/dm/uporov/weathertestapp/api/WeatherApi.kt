package com.github.dm.uporov.weathertestapp.api

import com.github.dm.uporov.weathertestapp.api.model.CurrentWeatherResponse
import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/forecast?units=metric")
    suspend fun getForecast(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
    ) : Response<ForecastResponse>

    @GET("/data/2.5/weather?units=metric")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
    ) : Response<CurrentWeatherResponse>
}
