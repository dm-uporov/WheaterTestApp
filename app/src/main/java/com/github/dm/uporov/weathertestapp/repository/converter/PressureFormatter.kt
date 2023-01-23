package com.github.dm.uporov.weathertestapp.repository.converter

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.math.roundToInt


interface PressureFormatter {

    fun format(pressure: Int?): String?
}

private const val HPA_PRESSURE_FORMAT = "%dhPa"

@ViewModelScoped
class PressureFormatterImpl @Inject constructor() : PressureFormatter {

    override fun format(pressure: Int?): String? {
        if (pressure == null) return null

        return String.format(HPA_PRESSURE_FORMAT, pressure)
    }
}