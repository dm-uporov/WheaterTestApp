package com.github.dm.uporov.weathertestapp.repository.converter

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.math.roundToInt


interface HumidityFormatter {

    fun format(humidity: Int?): String?
}

private const val HUMIDITY_FORMAT = "%d%%"

@ViewModelScoped
class HumidityFormatterImpl @Inject constructor() : HumidityFormatter {

    override fun format(humidity: Int?): String? {
        if (humidity == null) return null

        return String.format(HUMIDITY_FORMAT, humidity)
    }
}