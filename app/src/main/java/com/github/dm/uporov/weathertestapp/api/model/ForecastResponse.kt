package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("cod")
    val cod: String?,
    @SerializedName("message")
    val message: Int?,
    @SerializedName("cnt")
    val forecastsCount: Int,
    @SerializedName("list")
    val list: List<ForecastElement>,
    @SerializedName("city")
    val city: City?
)