package com.github.dm.uporov.weathertestapp.api

import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    //lat={lat}&lon={lon}&cnt={cnt}&appid={API key}
    // TODO move version to interceptors or http client definition
    @GET("/data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
    ) : Response<ForecastResponse>
}
