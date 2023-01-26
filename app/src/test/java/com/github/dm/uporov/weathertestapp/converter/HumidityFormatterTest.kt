package com.github.dm.uporov.weathertestapp.converter

import com.github.dm.uporov.weathertestapp.domain.converter.HumidityFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.HumidityFormatterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class HumidityFormatterTest {

    private val formatter: HumidityFormatter = HumidityFormatterImpl()

    @Test
    fun format_returnNullIfHumidityIsNull() {
        assertEquals(null, formatter.format(null))
    }

    @Test
    fun format() {
        assertEquals("19%", formatter.format(19))
    }
}