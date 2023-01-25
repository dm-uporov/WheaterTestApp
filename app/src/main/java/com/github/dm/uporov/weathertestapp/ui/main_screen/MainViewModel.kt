package com.github.dm.uporov.weathertestapp.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.dm.uporov.weathertestapp.domain.converter.ErrorFormatter
import com.github.dm.uporov.weathertestapp.domain.repository.ForecastRepository
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastDetailedItem
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val errorFormatter: ErrorFormatter,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MainUiState(
            isLoading = true,
            errorMessage = null,
            forecastShortItems = emptyList(),
            detailedItem = null
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var detailedItems: List<ForecastDetailedItem> = emptyList()
    private var selectedItem: Int = 0

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            try {
                val forecast = forecastRepository.getForecast()
                detailedItems = forecast.detailedItems
                if (selectedItem >= detailedItems.count()) {
                    selectedItem = 0
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        forecastShortItems = forecast.shortItems,
                        detailedItem = detailedItems[selectedItem]
                    )
                }
            } catch (e: Throwable) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorFormatter.format(e),
                        forecastShortItems = emptyList(),
                        detailedItem = null
                    )
                }
            }
        }
    }

    fun onForecastItemClicked(position: Int) {
        selectedItem = position
        _uiState.update {
            it.copy(
                isLoading = false,
                detailedItem = detailedItems[position]
            )
        }
    }

    fun onRetryClicked() {
        refresh()
    }
}