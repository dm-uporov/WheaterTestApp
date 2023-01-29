package com.github.dm.uporov.weathertestapp.ui.main_screen.model

sealed class MainUiState private constructor(
    val isLoading: Boolean,
    val errorMessage: String? = null,
    val forecastShortItems: List<ForecastShortItem> = emptyList(),
    val detailedItem: ForecastDetailedItem? = null
) {

    object Loading : MainUiState(isLoading = true)

    class Error(errorMessage: String) : MainUiState(isLoading = false, errorMessage = errorMessage)

    class Loaded(
        forecastShortItems: List<ForecastShortItem>,
        detailedItem: ForecastDetailedItem,
    ) : MainUiState(
        isLoading = false,
        forecastShortItems = forecastShortItems,
        detailedItem = detailedItem,
    )
}
