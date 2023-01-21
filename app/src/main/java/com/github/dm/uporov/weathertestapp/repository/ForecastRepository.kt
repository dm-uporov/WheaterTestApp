package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.ui.items.ForecastItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface ForecastRepository {
    val forecastList: Flow<List<ForecastItem>>
}

class ForecastRepositoryImpl @Inject constructor(
    remoteDataSource: ForecastRemoteDataSource,
    converter: ForecastItemConverter
) : ForecastRepository {

    override val forecastList: Flow<List<ForecastItem>> = remoteDataSource.latestForecast
        .filterNotNull()
        .map(converter::convert)
}