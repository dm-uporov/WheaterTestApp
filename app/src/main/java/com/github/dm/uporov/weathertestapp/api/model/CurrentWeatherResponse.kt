package com.github.dm.uporov.weathertestapp.api.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coordinates: Coordinates? = null,
    @SerializedName("weather")
    val weather: List<WeatherDescription>,
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("main")
    val detailedInfo: DetailedInfo,
    @SerializedName("visibility")
    val visibility: Int? = null,
    @SerializedName("wind")
    val wind: WindInfo? = null,
    @SerializedName("rain")
    val rain: Rain? = null,
    @SerializedName("clouds")
    val clouds: Clouds? = null,
    @SerializedName("dt")
    val dt: Int? = null,
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("timezone")
    val timezone: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val cityName: String? = null,
    @SerializedName("cod")
    val cod: Int? = null
)