package com.github.dm.uporov.weathertestapp.converter

import com.github.dm.uporov.weathertestapp.domain.converter.TemperatureFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.TemperatureFormatterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class TemperatureFormatterTest {

    private val formatter: TemperatureFormatter = TemperatureFormatterImpl()

    @Test
    fun format_returnNullIfPressureIsNull() {
        assertEquals(null, formatter.format(null))
    }

    @Test
    fun format() {
        assertEquals("17°C", formatter.format(17.0))
        assertEquals("16°C", formatter.format(15.9))
        assertEquals("15°C", formatter.format(15.3))
        assertEquals("-1°C", formatter.format(-1.0))
    }
}