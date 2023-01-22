package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.ui.model.ForecastUiModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface ForecastRepository {
    val forecastList: Flow<ForecastUiModel>
}

class ForecastRepositoryImpl @Inject constructor(
    remoteDataSource: ForecastRemoteDataSource,
    converter: ForecastItemsConverter
) : ForecastRepository {

    override val forecastList: Flow<ForecastUiModel> = remoteDataSource.latestForecast
        .filterNotNull()
        .map(converter::convert)
}