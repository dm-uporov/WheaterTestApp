package com.github.dm.uporov.weathertestapp.converter

import com.github.dm.uporov.weathertestapp.api.model.City
import com.github.dm.uporov.weathertestapp.domain.converter.ForecastItemsConverter
import com.github.dm.uporov.weathertestapp.domain.converter.ForecastItemsConverterImpl
import com.github.dm.uporov.weathertestapp.forecastResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ForecastItemsConverterTest {

    private val converter: ForecastItemsConverter = ForecastItemsConverterImpl(
        dateFormatter = mock {
            on { formatTime(anyOrNull()) } doReturn "Time"
            on { formatShortDate(anyOrNull()) } doReturn "ShortDate"
            on { formatDetailedDate(anyOrNull()) } doReturn "DetailedDate"
        },
        iconUrlFormatter = mock {
            on { formatUrl(anyOrNull()) } doReturn "IconUrl"
        },
        temperatureFormatter = mock {
            on { format(anyOrNull()) } doReturn "Temperature"
        },
        pressureFormatter = mock {
            on { format(anyOrNull()) } doReturn "Pressure"
        },
        humidityFormatter = mock {
            on { format(anyOrNull()) } doReturn "Humidity"
        },
        visibilityFormatter = mock {
            on { format(anyOrNull()) } doReturn "Visibility"
        },
        probabilityOfPrecipitationFormatter = mock {
            on { format(anyOrNull()) } doReturn "Precipitation"
        }
    )

    @Test
    fun convertResponseWithOneItem() {
        val responseWithOneItem = forecastResponse(1, city = City(name = "Izhevsk"))
        val result = converter.convert(responseWithOneItem)
        assertEquals(1, result.shortItems.count())
        assertEquals(1, result.detailedItems.count())

        val shortItem = result.shortItems[0]
        assertEquals(1L, shortItem.id)
        assertEquals("ShortDate", shortItem.date)
        assertEquals("Time", shortItem.time)
        assertEquals("IconUrl", shortItem.iconUrl)
        assertEquals("Temperature", shortItem.temperature)

        val detailedItem = result.detailedItems[0]
        assertEquals("Izhevsk", detailedItem.city)
        assertEquals("DetailedDate", detailedItem.date)
        assertEquals("Time", detailedItem.time)
        assertEquals("Temperature", detailedItem.temperature)
        assertEquals("Temperature", detailedItem.feelsLike)
        assertEquals("IconUrl", detailedItem.iconUrl)
        assertEquals("Pressure", detailedItem.pressure)
        assertEquals("Humidity", detailedItem.humidity)
        assertEquals("Visibility", detailedItem.visibility)
        assertEquals("Precipitation", detailedItem.probabilityOfPrecipitation)
    }

    @Test
    fun convertResponseWithManyItems_GetExactTheSameCountOfItems() {
        val responseWithOneItem = forecastResponse(16)
        val result = converter.convert(responseWithOneItem)
        assertEquals(16, result.shortItems.count())
        assertEquals(16, result.detailedItems.count())
    }
}