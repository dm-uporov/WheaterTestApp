package com.github.dm.uporov.weathertestapp.ui.model

import com.github.dm.uporov.weathertestapp.ui.model.ForecastDetailedItem
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem

data class MainUiState(
    val isLoading: Boolean = true,
    val forecastShortItems: List<ForecastShortItem> = emptyList(),
    val detailedItem: ForecastDetailedItem? = null
)
