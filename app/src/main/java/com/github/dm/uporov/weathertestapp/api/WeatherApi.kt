package com.github.dm.uporov.weathertestapp.api

import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // TODO move version to interceptors or http client definition
    @GET("/data/2.5/forecast")
    suspend fun getForecast(@Query("q") query: String) : Response<ForecastResponse>
}