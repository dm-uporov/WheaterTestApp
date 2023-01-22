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
    private val dateConverter: DateConverter,
    private val iconUrlHelper: OpenWeatherIconUrlHelper,
    private val temperatureConverter: TemperatureConverter
) : ForecastItemsConverter {

    override fun convert(response: ForecastResponse): ForecastUiModel {
        val shortItems = mutableListOf<ForecastShortItem>()
        val detailedItems = mutableListOf<ForecastDetailedItem>()

        val city = response.city?.name

        response.list.forEach {

            shortItems.add(
                ForecastShortItem(
                    id = it.date,
                    dateTitle = dateConverter.convertTimestampToShortDate(it.date),
                    iconUrl = iconUrlHelper.createUrl(it.weatherDescriptions?.firstOrNull()?.icon),
                    temperatureMax = temperatureConverter.convert(it.detailedInfo?.tempMax),
                    temperatureMin = temperatureConverter.convert(it.detailedInfo?.tempMin),
                )
            )

            detailedItems.add(
                ForecastDetailedItem(
                    city = city,
                    dateTitle = dateConverter.convertTimestampToDetailedDate(it.date),
                    temperature = temperatureConverter.convert(it.detailedInfo?.temp)
                )
            )
        }

        return ForecastUiModel(
            shortItems = shortItems,
            detailedItems = detailedItems
        )
    }
}