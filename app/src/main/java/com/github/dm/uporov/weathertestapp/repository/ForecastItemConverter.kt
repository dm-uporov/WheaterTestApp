package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import com.github.dm.uporov.weathertestapp.ui.items.ForecastItem
import javax.inject.Inject

interface ForecastItemConverter {

    fun convert(response: ForecastResponse): List<ForecastItem>
}

class ForecastItemConverterImpl @Inject constructor() : ForecastItemConverter {

    override fun convert(response: ForecastResponse): List<ForecastItem> {
        return response.forecastsList.map {
            ForecastItem(it.dtTxt ?: "No date")
        }
    }
}