package com.github.dm.uporov.weathertestapp.repository.converter

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.math.roundToInt


interface TemperatureFormatter {

    fun format(temperature: Double?): String?
}

private const val CELSIUS_TEMPERATURE_FORMAT = "%d°C"

@ViewModelScoped
class TemperatureFormatterImpl @Inject constructor() : TemperatureFormatter {

    override fun format(temperature: Double?): String? {
        if (temperature == null) return null

        return String.format(CELSIUS_TEMPERATURE_FORMAT, temperature.roundToInt())
    }
}