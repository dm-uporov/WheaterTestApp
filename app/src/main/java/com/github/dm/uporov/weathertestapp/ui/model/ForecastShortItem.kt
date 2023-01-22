package com.github.dm.uporov.weathertestapp.ui.model

data class ForecastShortItem(
    val id: Long,
    val dateTitle: String,
    val iconUrl: String?,
    val temperatureMax: String?,
    val temperatureMin: String?,
)
