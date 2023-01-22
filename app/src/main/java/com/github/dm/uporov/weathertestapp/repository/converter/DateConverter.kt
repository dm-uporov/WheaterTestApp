package com.github.dm.uporov.weathertestapp.repository.converter

import dagger.hilt.android.scopes.ViewModelScoped
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface DateConverter {

    fun convertTimestampToShortDate(timestamp: Long): String

    fun convertTimestampToDetailedDate(timestamp: Long): String
}

private const val SHORT_PATTERN = "EEE, d MMM"
private const val DETAILED_PATTERN = "EEE, d MMM"

// TODO
private const val NOW_TEXT = "Now"
private const val TODAY_TEXT = "Today"

@ViewModelScoped
class DateConverterImpl @Inject constructor() : DateConverter {

    private val shortDateFormat = SimpleDateFormat(SHORT_PATTERN, Locale.ENGLISH)
    private val detailedDateFormat = SimpleDateFormat(DETAILED_PATTERN, Locale.ENGLISH)

    override fun convertTimestampToShortDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return shortDateFormat.format(date)
    }

    override fun convertTimestampToDetailedDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return detailedDateFormat.format(date)
    }
}