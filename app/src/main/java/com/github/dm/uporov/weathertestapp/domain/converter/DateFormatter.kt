package com.github.dm.uporov.weathertestapp.domain.converter

import android.content.res.Resources
import com.github.dm.uporov.weathertestapp.R
import dagger.hilt.android.scopes.ViewModelScoped
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface DateFormatter {

    fun formatShortDate(date: Date): String

    fun formatTime(date: Date): String

    fun formatDetailedDate(date: Date): String
}

private const val SHORT_PATTERN = "EEE, d MMM"
private const val HOURS_PATTERN = "HH:mm"
private const val DETAILED_PATTERN = "EEEE, d MMMM, HH:mm"

@ViewModelScoped
class DateFormatterImpl @Inject constructor(
    resources: Resources,
) : DateFormatter {

    private val shortDateFormat = SimpleDateFormat(SHORT_PATTERN, Locale.ENGLISH)
    private val hoursDateFormat = SimpleDateFormat(HOURS_PATTERN, Locale.ENGLISH)
    private val detailedDateFormat = SimpleDateFormat(DETAILED_PATTERN, Locale.ENGLISH)

    private val calendar = Calendar.getInstance(TimeZone.getDefault())
    private val todayDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    private val todayText: String = resources.getString(R.string.today)

    override fun formatShortDate(date: Date): String {
        calendar.time = date
        return if (calendar.get(Calendar.DAY_OF_YEAR) == todayDayOfYear) {
            todayText
        } else {
            shortDateFormat.format(date)
        }
    }

    override fun formatTime(date: Date): String = hoursDateFormat.format(date)

    override fun formatDetailedDate(date: Date): String = detailedDateFormat.format(date)
}