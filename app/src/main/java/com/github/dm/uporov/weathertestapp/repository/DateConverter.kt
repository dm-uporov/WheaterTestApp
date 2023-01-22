package com.github.dm.uporov.weathertestapp.repository

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface DateConverter {

    fun convertTimestampToShortDate(timestamp: Long): String

    fun convertTimestampToDetailedDate(timestamp: Long): String
}

class DateConverterImpl @Inject constructor() : DateConverter {

    private val shortDateFormat = SimpleDateFormat("EEE, d MMM", Locale.ENGLISH)

    override fun convertTimestampToShortDate(timestamp: Long): String {
        return shortDateFormat.format(Date(timestamp * 1000))
    }

    override fun convertTimestampToDetailedDate(timestamp: Long): String {
        // TODO("Not yet implemented")
        return "TODO"
    }
}