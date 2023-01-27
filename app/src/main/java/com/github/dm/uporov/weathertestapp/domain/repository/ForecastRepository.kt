package com.github.dm.uporov.weathertestapp.domain.repository

import android.location.Location
import com.github.dm.uporov.weathertestapp.api.exception.ServerErrorException
import com.github.dm.uporov.weathertestapp.domain.converter.ForecastItemsConverter
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationFetchingException
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationPermissionDeniedException
import com.github.dm.uporov.weathertestapp.domain.source.ForecastRemoteDataSource
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastUiModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface ForecastRepository {

    @Throws(LocationPermissionDeniedException::class, LocationFetchingException::class)
    suspend fun getForecast(): ForecastUiModel
}

@ViewModelScoped
class ForecastRepositoryImpl @Inject constructor(
    private val forecastDataSource: ForecastRemoteDataSource,
    private val locationRepository: LocationRepository,
    private val converter: ForecastItemsConverter,
) : ForecastRepository {

    override suspend fun getForecast(): ForecastUiModel {
        val location: Location = try {
            locationRepository.getCurrentLocation()
        } catch (e: LocationPermissionDeniedException) {
            throw e
        } catch (e: Throwable) {
            locationRepository.getLastLocation()
        } ?: throw LocationFetchingException()

        val forecastResponse = forecastDataSource.getForecast(location.latitude, location.longitude)
            ?: throw ServerErrorException()

        return converter.convert(forecastResponse)
    }
}