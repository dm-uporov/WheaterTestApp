package com.github.dm.uporov.weathertestapp.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.dm.uporov.weathertestapp.domain.converter.ErrorFormatter
import com.github.dm.uporov.weathertestapp.domain.repository.ForecastRepository
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastUiModel
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var loadedForecast: ForecastUiModel? = null
    private var selectedItem: Int = 0

    private var job: Job? = null

    init {
        refresh()
        refresh()
    }

    private fun refresh() {
        job?.cancel()
        _uiState.update { MainUiState.Loading }

        job = viewModelScope.launch {
            try {
                val forecast = forecastRepository.getForecast()
                loadedForecast = forecast
                if (selectedItem >= forecast.detailedItems.count()) {
                    selectedItem = 0
                }
                _uiState.update {
                    MainUiState.Loaded(forecast.shortItems, forecast.detailedItems[selectedItem])
                }
            } catch (e: CancellationException) {
                // Do nothing
            } catch (e: Throwable) {
                _uiState.update {
                    MainUiState.Error(errorFormatter.format(e))
                }
            }
        }
    }

    fun onForecastItemClicked(position: Int) {
        if (position == selectedItem) return

        selectedItem = position

        val forecast = loadedForecast
        // it is unlikely, but allows us not to use '?' operator
        if (forecast == null) {
            refresh()
        } else {
            _uiState.update {
                MainUiState.Loaded(forecast.shortItems, forecast.detailedItems[position])
            }
        }
    }

    fun onRetryClicked() {
        refresh()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}