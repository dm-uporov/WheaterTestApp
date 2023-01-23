package com.github.dm.uporov.weathertestapp.ui.model

data class ForecastDetailedItem(
    val city: String?,
    val date: String,
    val time: String,
    val temperature: String?,
    val feelsLike: String?,
    val iconUrl: String?,
    val pressure: String?,
    val humidity: String?,
    val visibility: String?,
    val probabilityOfPrecipitation: String?,
)
