package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("cod")
    val cod: String? = null,
    @SerializedName("message")
    val message: Int? = null,
    @SerializedName("cnt")
    val forecastsCount: Int,
    @SerializedName("list")
    val list: List<ForecastElement>,
    @SerializedName("city")
    val city: City? = null
)