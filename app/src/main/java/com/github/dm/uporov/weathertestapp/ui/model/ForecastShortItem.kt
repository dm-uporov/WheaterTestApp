package com.github.dm.uporov.weathertestapp.ui.model

data class ForecastShortItem(
    val id: Long,
    val date: String,
    val time: String,
    val iconUrl: String?,
    val temperature: String?,
)
