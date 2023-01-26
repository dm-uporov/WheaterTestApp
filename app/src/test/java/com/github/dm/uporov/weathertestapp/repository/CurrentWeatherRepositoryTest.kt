package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.currentWeatherResponse
import com.github.dm.uporov.weathertestapp.domain.repository.CurrentWeatherRepository
import com.github.dm.uporov.weathertestapp.domain.repository.CurrentWeatherRepositoryImpl
import com.github.dm.uporov.weathertestapp.domain.repository.LocationRepository
import com.github.dm.uporov.weathertestapp.domain.source.CurrentWeatherRemoteDataSource
import com.github.dm.uporov.weathertestapp.locationMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.kotlin.*

class CurrentWeatherRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCurrentWeather_callApiWithCorrectLocation() = runTest {
        val locationMock = locationMock(12.345, 13.4567)
        val locationRepositoryMock = mock<LocationRepository> {
            onBlocking { getLastLocation() } doReturn locationMock
        }
        val remoteDataSourceMock = mock<CurrentWeatherRemoteDataSource> {
            onBlocking { getCurrentWeather(any(), any()) } doReturn currentWeatherResponse()
        }
        val repository: CurrentWeatherRepository = CurrentWeatherRepositoryImpl(
            remoteDataSource = remoteDataSourceMock,
            locationRepository = locationRepositoryMock,
        )

        /* When */
        val weather = repository.getCurrentWeather()
        /* Then */
        assertNotNull(weather)
        verify(locationRepositoryMock).getLastLocation()
        verify(remoteDataSourceMock).getCurrentWeather(12.345, 13.4567)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCurrentWeather_doNotCallApiWhenLocationReturnedException() = runTest {
        val locationRepositoryMock = mock<LocationRepository> {
            onBlocking { getLastLocation() } doThrow RuntimeException()
        }
        val remoteDataSourceMock = mock<CurrentWeatherRemoteDataSource> {
            onBlocking { getCurrentWeather(any(), any()) } doReturn currentWeatherResponse()
        }
        val repository: CurrentWeatherRepository = CurrentWeatherRepositoryImpl(
            remoteDataSource = remoteDataSourceMock,
            locationRepository = locationRepositoryMock,
        )

        /* When */
        val weather = repository.getCurrentWeather()
        /* Then */
        assertNull(weather)
        verify(locationRepositoryMock).getLastLocation()
        verifyNoInteractions(remoteDataSourceMock)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCurrentWeather_onApiExceptionReturnNull() = runTest {
        val locationMock = locationMock(76.54, 65.432)
        val locationRepositoryMock = mock<LocationRepository> {
            onBlocking { getLastLocation() } doReturn locationMock
        }
        val remoteDataSourceMock = mock<CurrentWeatherRemoteDataSource> {
            onBlocking { getCurrentWeather(any(), any()) } doThrow RuntimeException()
        }
        val repository: CurrentWeatherRepository = CurrentWeatherRepositoryImpl(
            remoteDataSource = remoteDataSourceMock,
            locationRepository = locationRepositoryMock,
        )

        /* When */
        val weather = repository.getCurrentWeather()
        /* Then */
        assertNull(weather)
        verify(locationRepositoryMock).getLastLocation()
        verify(remoteDataSourceMock).getCurrentWeather(76.54, 65.432)
    }
}