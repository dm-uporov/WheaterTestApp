package com.github.dm.uporov.weathertestapp.repository.converter

import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import com.github.dm.uporov.weathertestapp.ui.model.ForecastDetailedItem
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem
import com.github.dm.uporov.weathertestapp.ui.model.ForecastUiModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface ForecastItemsConverter {

    fun convert(response: ForecastResponse): ForecastUiModel
}

@ViewModelScoped
class ForecastItemsConverterImpl @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val iconUrlFormatter: OpenWeatherIconUrlFormatter,
    private val temperatureFormatter: TemperatureFormatter,
    private val pressureFormatter: PressureFormatter,
    private val humidityFormatter: HumidityFormatter,
    private val visibilityFormatter: VisibilityFormatter,
    private val probabilityOfPrecipitationFormatter: ProbabilityOfPrecipitationFormatter,
) : ForecastItemsConverter {

    override fun convert(response: ForecastResponse): ForecastUiModel {
        val shortItems = mutableListOf<ForecastShortItem>()
        val detailedItems = mutableListOf<ForecastDetailedItem>()

        val city = response.city?.name

        response.list.forEach {
            val time = dateFormatter.formatTime(it.date)
            val iconUrl = iconUrlFormatter.formatUrl(it.weatherDescriptions?.firstOrNull()?.icon)
            val temperature = temperatureFormatter.format(it.detailedInfo?.temp)

            shortItems.add(
                ForecastShortItem(
                    id = it.date.time,
                    date = dateFormatter.formatShortDate(it.date),
                    time = time,
                    iconUrl = iconUrl,
                    temperature = temperature,
                )
            )

            detailedItems.add(
                ForecastDetailedItem(
                    city = city,
                    date = dateFormatter.formatDetailedDate(it.date),
                    time = time,
                    temperature = temperature,
                    feelsLike = temperatureFormatter.format(it.detailedInfo?.feelsLike),
                    iconUrl = iconUrl,
                    pressure = pressureFormatter.format(it.detailedInfo?.pressurePHa),
                    humidity = humidityFormatter.format(it.detailedInfo?.humidityPercents),
                    visibility = visibilityFormatter.format(it.visibilityMeters),
                    probabilityOfPrecipitation = probabilityOfPrecipitationFormatter.format(it.probabilityOffPrecipitation)
                )
            )
        }

        return ForecastUiModel(
            shortItems = shortItems,
            detailedItems = detailedItems
        )
    }
}