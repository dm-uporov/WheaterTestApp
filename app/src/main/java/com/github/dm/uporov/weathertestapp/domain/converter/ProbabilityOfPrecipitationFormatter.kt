package com.github.dm.uporov.weathertestapp.domain.converter

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


interface ProbabilityOfPrecipitationFormatter {

    fun format(probabilityOfPrecipitation: Double?): String?
}

private const val PROBABILITY_OF_PRECIPITATION_FORMAT = "%.0f%%"

@ViewModelScoped
class ProbabilityOfPrecipitationFormatterImpl @Inject constructor() : ProbabilityOfPrecipitationFormatter {

    override fun format(probabilityOfPrecipitation: Double?): String? {
        if (probabilityOfPrecipitation == null) return null

        return String.format(PROBABILITY_OF_PRECIPITATION_FORMAT, probabilityOfPrecipitation * 100)
    }
}