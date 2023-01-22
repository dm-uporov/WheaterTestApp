package com.github.dm.uporov.weathertestapp.repository

import com.github.dm.uporov.weathertestapp.api.model.ForecastResponse
import com.github.dm.uporov.weathertestapp.ui.model.ForecastDetailedItem
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem
import com.github.dm.uporov.weathertestapp.ui.model.ForecastUiModel
import javax.inject.Inject

interface ForecastItemsConverter {

    fun convert(response: ForecastResponse): ForecastUiModel
}

class ForecastItemsConverterImpl @Inject constructor(
    private val dateConverter: DateConverter,
    private val iconUrlHelper: OpenWeatherIconUrlHelper,
) : ForecastItemsConverter {

    override fun convert(response: ForecastResponse): ForecastUiModel {
        val shortItems = mutableListOf<ForecastShortItem>()
        val detailedItems = mutableListOf<ForecastDetailedItem>()

        response.list.forEach {

            shortItems.add(
                ForecastShortItem(
                    dateTitle = dateConverter.convertTimestampToShortDate(it.date),
                    iconUrl = iconUrlHelper.createUrl(it.weatherDescriptions?.firstOrNull()?.icon),
                    dayTemperature = "+7",
                    nightTemperature = "-3",
                )
            )

            detailedItems.add(
                ForecastDetailedItem(
                    dateTitle = dateConverter.convertTimestampToDetailedDate(it.date)
                )
            )
        }

        return ForecastUiModel(
            shortItems = shortItems,
            detailedItems = detailedItems
        )
    }
}