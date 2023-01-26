package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ForecastElement(
    @SerializedName("dt")
    val date: Timestamp,
    @SerializedName("main")
    val detailedInfo: DetailedInfo? = null,
    @SerializedName("weather")
    val weatherDescriptions: List<WeatherDescription>? = null,
    @SerializedName("clouds")
    val clouds: Clouds? = null,
    @SerializedName("wind")
    val wind: WindInfo? = null,
    @SerializedName("visibility")
    val visibilityMeters: Int? = null,
    @SerializedName("pop")
    val probabilityOfPrecipitation: Double? = null,
    @SerializedName("rain")
    val rain: Rain? = null,
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("dt_txt")
    val dtTxt: String? = null
)