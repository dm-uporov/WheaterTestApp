package com.github.dm.uporov.weathertestapp.ui.main_screen.model

data class ForecastShortItem(
    val id: Long,
    val date: String,
    val time: String,
    val iconUrl: String?,
    val temperature: String?,
)
