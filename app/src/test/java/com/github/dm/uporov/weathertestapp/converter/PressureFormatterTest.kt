package com.github.dm.uporov.weathertestapp.converter

import com.github.dm.uporov.weathertestapp.domain.converter.PressureFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.PressureFormatterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class PressureFormatterTest {

    private val formatter: PressureFormatter = PressureFormatterImpl()

    @Test
    fun format_returnNullIfPressureIsNull() {
        assertEquals(null, formatter.format(null))
    }

    @Test
    fun format() {
        assertEquals("1054hPa", formatter.format(1054))
    }
}