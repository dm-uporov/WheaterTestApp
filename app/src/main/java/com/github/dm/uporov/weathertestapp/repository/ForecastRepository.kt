package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.repository.converter.ForecastItemsConverter
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastUiModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface ForecastRepository {
    val forecastList: Flow<ForecastUiModel>
}

@ViewModelScoped
class ForecastRepositoryImpl @Inject constructor(
    remoteDataSource: ForecastRemoteDataSource,
    converter: ForecastItemsConverter
) : ForecastRepository {

    override val forecastList: Flow<ForecastUiModel> = remoteDataSource.latestForecast
        .filterNotNull()
        .map(converter::convert)
}