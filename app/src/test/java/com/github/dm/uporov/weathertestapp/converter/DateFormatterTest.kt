package com.github.dm.uporov.weathertestapp.converter

import android.content.res.Resources
import com.github.dm.uporov.weathertestapp.domain.converter.DateFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.DateFormatterImpl
import org.junit.Assert.assertEquals
import org.junit.Test

import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.*

class DateFormatterTest {

    private val resourcesMock = mock<Resources> {
        on { getString(any()) } doReturn "Today"
    }
    private val formatter: DateFormatter = DateFormatterImpl(resourcesMock)

    @Test
    fun formatShortDate_returnTodayWhenItIsToday() {
        val today = Date()
        assertEquals("Today", formatter.formatShortDate(today))
    }

    @Test
    fun formatShortDate() {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.set(2022, 2, 22, 22, 22)
        val date = Date.from(calendar.toInstant())
        assertEquals("Tue, 22 Mar", formatter.formatShortDate(date))
    }

    @Test
    fun formatDetailedDate_returnDetailedDateEvenIfItIsToday() {
        val today = Date()
        assert("Today" != formatter.formatDetailedDate(today))

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.set(2023, 0, 25, 19, 25)
        val date = Date.from(calendar.toInstant())

        assertEquals("Wednesday, 25 January, 19:25", formatter.formatDetailedDate(date))
    }

    @Test
    fun formatTime() {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.set(1992, 9, 9, 12, 34)
        val date = Date.from(calendar.toInstant())
        assertEquals("12:34", formatter.formatTime(date))
    }
}