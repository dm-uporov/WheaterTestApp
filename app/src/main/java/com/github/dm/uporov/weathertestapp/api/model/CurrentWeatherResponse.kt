package com.github.dm.uporov.weathertestapp.api.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("weather")
    val weather: List<WeatherDescription>,
    @SerializedName("base")
    val base: String?,
    @SerializedName("main")
    val detailedInfo: DetailedInfo,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("wind")
    val wind: WindInfo?,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("clouds")
    val clouds: Clouds?,
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("timezone")
    val timezone: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val cityName: String?,
    @SerializedName("cod")
    val cod: Int?
)