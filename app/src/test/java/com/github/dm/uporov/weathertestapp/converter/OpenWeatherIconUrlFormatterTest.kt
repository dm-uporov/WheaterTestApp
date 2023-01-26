package com.github.dm.uporov.weathertestapp.converter

import com.github.dm.uporov.weathertestapp.domain.converter.OpenWeatherIconUrlFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.OpenWeatherIconUrlFormatterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class OpenWeatherIconUrlFormatterTest {

    private val formatter: OpenWeatherIconUrlFormatter = OpenWeatherIconUrlFormatterImpl()

    @Test
    fun format_returnNullIfIconIdIsNull() {
        assertEquals(null, formatter.formatUrl(null))
    }

    @Test
    fun format() {
        assertEquals("https://openweathermap.org/img/wn/123abc@2x.png", formatter.formatUrl("123abc"))
    }
}