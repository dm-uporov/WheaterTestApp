package com.github.dm.uporov.weathertestapp.converter

import android.content.res.Resources
import com.github.dm.uporov.weathertestapp.domain.converter.VisibilityFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.VisibilityFormatterImpl
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class VisibilityFormatterTest {

    private val resourcesMock = mock<Resources> {
        on { getString(any()) } doReturn "Maximum"
    }
    private val formatter: VisibilityFormatter = VisibilityFormatterImpl(resourcesMock)

    @Test
    fun format_returnNullIfPressureIsNull() {
        assertEquals(null, formatter.format(null))
    }

    @Test
    fun format() {
        assertEquals("1700m", formatter.format(1700))
        assertEquals("9999m", formatter.format(9999))
        assertEquals("Maximum", formatter.format(10_000))
        assertEquals("Maximum", formatter.format(10_001))
        assertEquals("Maximum", formatter.format(100_000))
    }
}