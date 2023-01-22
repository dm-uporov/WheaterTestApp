package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class ForecastElement(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("main")
    val detailedInfo: DetailedInfo?,
    @SerializedName("weather")
    val weatherDescriptions: List<WeatherDescription>?,
    @SerializedName("clouds")
    val clouds: Clouds?,
    @SerializedName("wind")
    val wind: WindInfo?,
    @SerializedName("visibility")
    val visibilityMeters: Int?,
    @SerializedName("pop")
    val probabilityOffPrecipitation: Double?,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("dt_txt")
    val dtTxt: String?
)