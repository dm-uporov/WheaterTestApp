package com.github.dm.uporov.weathertestapp.ui

import com.github.dm.uporov.weathertestapp.ui.items.ForecastItem

data class MainUiState(
    val isLoading: Boolean = false,
    val forecastItems: List<ForecastItem> = emptyList()
)
