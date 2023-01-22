package com.github.dm.uporov.weathertestapp.repository.converter

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.math.roundToInt


interface TemperatureConverter {

    fun convert(temperature: Double?): String?
}

private const val CELSIUS_TEMPERATURE_FORMAT = "%d°C"

@ViewModelScoped
class TemperatureConverterImpl @Inject constructor() : TemperatureConverter {

    override fun convert(temperature: Double?): String? {
        if (temperature == null) return null

        return String.format(CELSIUS_TEMPERATURE_FORMAT, temperature.roundToInt())
    }
}