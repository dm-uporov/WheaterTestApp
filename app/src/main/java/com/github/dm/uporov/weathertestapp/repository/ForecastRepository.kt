package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.repository.converter.ForecastItemsConverter
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastUiModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ForecastRepository {
    val forecast: Flow<LoadingState<ForecastUiModel>>

    fun refresh(scope: CoroutineScope)
}

private const val SERVER_ERROR = "Server's error"

@ViewModelScoped
class ForecastRepositoryImpl @Inject constructor(
    private val remoteDataSource: ForecastRemoteDataSource,
    private val converter: ForecastItemsConverter
) : ForecastRepository {

    override val forecast = MutableStateFlow<LoadingState<ForecastUiModel>>(LoadingState.Loading())

    override fun refresh(scope: CoroutineScope) {
        scope.launch {
            forecast.emit(LoadingState.Loading())

            val response = remoteDataSource.getForecast()
            if (response == null) {
                forecast.emit(LoadingState.Error(SERVER_ERROR))
                return@launch
            }

            val result = converter.convert(response)
            forecast.emit(LoadingState.Loaded(result))
        }
    }
}