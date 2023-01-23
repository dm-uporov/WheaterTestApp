package com.github.dm.uporov.weathertestapp.ui.main_screen.model

data class MainUiState(
    val isLoading: Boolean = true,
    val forecastShortItems: List<ForecastShortItem> = emptyList(),
    val detailedItem: ForecastDetailedItem? = null
)
