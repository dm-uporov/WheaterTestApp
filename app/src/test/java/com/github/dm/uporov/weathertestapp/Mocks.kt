package com.github.dm.uporov.weathertestapp

import android.location.Location
import com.github.dm.uporov.weathertestapp.api.model.*
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastUiModel
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.sql.Timestamp

fun locationMock(lat: Double = 0.1, lon: Double = 0.2) = mock<Location> {
    on { latitude } doReturn lat
    on { longitude } doReturn lon
}

fun currentWeatherResponse(): CurrentWeatherResponse {
    return CurrentWeatherResponse(
        weather = emptyList(),
        detailedInfo = DetailedInfo(temp = 1.0),
    )
}

fun forecastResponse(
    count: Int,
    city: City? = null,
): ForecastResponse {
    val elements = List(count) {
        ForecastElement(date = Timestamp(it + 1L))
    }
    return ForecastResponse(
        cod = null,
        message = null,
        forecastsCount = 1,
        elements,
        city = city,
    )
}

fun forecastUiModel() = ForecastUiModel(
    shortItems = emptyList(),
    detailedItems = emptyList(),
)