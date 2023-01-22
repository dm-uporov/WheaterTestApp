package com.github.dm.uporov.weathertestapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.dm.uporov.weathertestapp.repository.ForecastRepository
import com.github.dm.uporov.weathertestapp.ui.model.ForecastDetailedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var detailedItems: List<ForecastDetailedItem> = emptyList()
    private val selectedItem: Int = 0

    init {
        viewModelScope.launch {
            forecastRepository.forecastList.collect { uiModel ->
                _uiState.update {
                    detailedItems = uiModel.detailedItems
                    it.copy(
                        isLoading = false,
                        forecastShortItems = uiModel.shortItems,
                        detailedItem = uiModel.detailedItems[selectedItem]
                    )
                }
            }
        }
    }
}