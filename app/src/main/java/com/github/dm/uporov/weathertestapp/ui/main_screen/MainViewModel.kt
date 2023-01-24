package com.github.dm.uporov.weathertestapp.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.dm.uporov.weathertestapp.repository.ForecastRepository
import com.github.dm.uporov.weathertestapp.repository.LoadingState
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
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var detailedItems: List<ForecastDetailedItem> = emptyList()
    private var selectedItem: Int = 0

    init {
        viewModelScope.launch {
            forecastRepository.forecast.collect { loadingState ->
                _uiState.update {
                    when (loadingState) {
                        is LoadingState.Loading -> it.copy(
                            isLoading = true,
                            error = false
                        )
                        is LoadingState.Loaded -> {
                            detailedItems = loadingState.data.detailedItems
                            if (selectedItem >= detailedItems.count()) {
                                selectedItem = 0
                            }
                            it.copy(
                                isLoading = false,
                                error = false,
                                forecastShortItems = loadingState.data.shortItems,
                                detailedItem = detailedItems[selectedItem]
                            )
                        }
                        is LoadingState.Error -> it.copy(
                            isLoading = false,
                            error = true
                        )
                    }
                }
            }
        }
        forecastRepository.refresh(viewModelScope)
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
}