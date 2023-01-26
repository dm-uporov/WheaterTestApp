package com.github.dm.uporov.weathertestapp.converter

import com.github.dm.uporov.weathertestapp.domain.converter.ProbabilityOfPrecipitationFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.ProbabilityOfPrecipitationFormatterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class ProbabilityOfPrecipitationFormatterTest {

    private val formatter: ProbabilityOfPrecipitationFormatter =
        ProbabilityOfPrecipitationFormatterImpl()

    @Test
    fun format_returnNullIfPressureIsNull() {
        assertEquals(null, formatter.format(null))
    }

    @Test
    fun format() {
        assertEquals("97%", formatter.format(0.97))
        assertEquals("1%", formatter.format(0.01))
        assertEquals("1%", formatter.format(0.007))
        assertEquals("0%", formatter.format(0.001))
    }
}