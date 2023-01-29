package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.api.exception.ServerErrorException
import com.github.dm.uporov.weathertestapp.domain.converter.ForecastItemsConverter
import com.github.dm.uporov.weathertestapp.domain.exception.LocationPermissionDeniedException
import com.github.dm.uporov.weathertestapp.domain.repository.ForecastRepository
import com.github.dm.uporov.weathertestapp.domain.repository.ForecastRepositoryImpl
import com.github.dm.uporov.weathertestapp.domain.repository.LocationRepository
import com.github.dm.uporov.weathertestapp.api.datasource.ForecastRemoteDataSource
import com.github.dm.uporov.weathertestapp.forecastResponse
import com.github.dm.uporov.weathertestapp.forecastUiModel
import com.github.dm.uporov.weathertestapp.locationMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Test
import org.mockito.kotlin.*

class ForecastRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getForecast_callApiWithCurrentLocation() = runTest {
        val locationMock = locationMock(11.22, 33.44)
        val locationRepositoryMock = mock<LocationRepository> {
            onBlocking { getCurrentLocation() } doReturn locationMock
        }
        val forecastResponse = forecastResponse(3)
        val forecastDataSourceMock = mock<ForecastRemoteDataSource> {
            onBlocking { getForecast(any(), any()) } doReturn forecastResponse
        }
        val forecast = forecastUiModel()
        val converterMock = mock<ForecastItemsConverter> {
            on { convert(any()) } doReturn forecast
        }

        val repository: ForecastRepository = ForecastRepositoryImpl(
            forecastDataSource = forecastDataSourceMock,
            locationRepository = locationRepositoryMock,
            converter = converterMock,
        )

        /* When */
        val result = repository.getForecast()
        /* Then */
        assertSame(forecast, result)
        verify(locationRepositoryMock).getCurrentLocation()
        verifyNoMoreInteractions(locationRepositoryMock)
        verify(forecastDataSourceMock).getForecast(11.22, 33.44)
        verify(converterMock).convert(forecastResponse)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = LocationPermissionDeniedException::class)
    fun getForecast_NoLocationPermissionForCurrentLocation_throwException() = runTest {
        val locationRepositoryMock = mock<LocationRepository> {
            onBlocking { getCurrentLocation() } doThrow LocationPermissionDeniedException()
        }
        val forecastDataSourceMock = mock<ForecastRemoteDataSource> {
            onBlocking { getForecast(any(), any()) } doReturn forecastResponse(1)
        }
        val converterMock = mock<ForecastItemsConverter> {
            on { convert(any()) } doReturn forecastUiModel()
        }

        val repository: ForecastRepository = ForecastRepositoryImpl(
            forecastDataSource = forecastDataSourceMock,
            locationRepository = locationRepositoryMock,
            converter = converterMock,
        )

        /* Should throw LocationPermissionDeniedException */
        repository.getForecast()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getForecast_CurrentLocationThrows_UsedLastLocation() = runTest {
        val locationMock = locationMock(22.33, 44.55)
        val locationRepositoryMock = mock<LocationRepository> {
            onBlocking { getCurrentLocation() } doThrow RuntimeException()
            onBlocking { getLastLocation() } doReturn locationMock
        }
        val forecastDataSourceMock = mock<ForecastRemoteDataSource> {
            onBlocking { getForecast(any(), any()) } doReturn forecastResponse(1)
        }
        val converterMock = mock<ForecastItemsConverter> {
            on { convert(any()) } doReturn forecastUiModel()
        }

        val repository: ForecastRepository = ForecastRepositoryImpl(
            forecastDataSource = forecastDataSourceMock,
            locationRepository = locationRepositoryMock,
            converter = converterMock,
        )

        /* When */
        val result = repository.getForecast()
        /* Then */
        assertNotNull(result)
        verify(locationRepositoryMock).getCurrentLocation()
        verify(locationRepositoryMock).getLastLocation()
        verify(forecastDataSourceMock).getForecast(22.33, 44.55)
        verify(converterMock).convert(any())
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = ServerErrorException::class)
    fun getForecast_dataSourceReturnedNull_throwServerErrorException() = runTest {
        val locationMock = locationMock()
        val locationRepositoryMock = mock<LocationRepository> {
            onBlocking { getCurrentLocation() } doReturn locationMock
        }
        val forecastDataSourceMock = mock<ForecastRemoteDataSource> {
            onBlocking { getForecast(any(), any()) } doReturn null
        }
        val converterMock = mock<ForecastItemsConverter> {
            on { convert(any()) } doReturn forecastUiModel()
        }

        val repository: ForecastRepository = ForecastRepositoryImpl(
            forecastDataSource = forecastDataSourceMock,
            locationRepository = locationRepositoryMock,
            converter = converterMock,
        )

        /* Should throw ServerErrorException */
        repository.getForecast()
    }
}