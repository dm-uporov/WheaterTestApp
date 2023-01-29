package com.github.dm.uporov.weathertestapp.domain.repository

import com.github.dm.uporov.weathertestapp.api.model.CurrentWeatherResponse
import com.github.dm.uporov.weathertestapp.domain.exception.LocationFetchingException
import com.github.dm.uporov.weathertestapp.domain.exception.LocationPermissionDeniedException
import com.github.dm.uporov.weathertestapp.api.datasource.CurrentWeatherRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

interface CurrentWeatherRepository {

    @Throws(LocationPermissionDeniedException::class, LocationFetchingException::class)
    suspend fun getCurrentWeather(): CurrentWeatherResponse?
}

@Singleton
class CurrentWeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: CurrentWeatherRemoteDataSource,
    private val locationRepository: LocationRepository,
) : CurrentWeatherRepository {

    override suspend fun getCurrentWeather(): CurrentWeatherResponse? {
        val location = locationRepository.getLastLocation() ?: throw LocationFetchingException()

        return remoteDataSource.getCurrentWeather(location.latitude, location.longitude)
    }
}