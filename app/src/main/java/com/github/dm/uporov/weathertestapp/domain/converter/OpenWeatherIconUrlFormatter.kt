package com.github.dm.uporov.weathertestapp.domain.converter

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


interface OpenWeatherIconUrlFormatter {

    fun formatUrl(iconId: String?): String?
}

private const val OPEN_WEATHER_ICONS_URL_FORMAT = "https://openweathermap.org/img/wn/%s@2x.png"

@ViewModelScoped
class OpenWeatherIconUrlFormatterImpl @Inject constructor() : OpenWeatherIconUrlFormatter {

    override fun formatUrl(iconId: String?): String? {
        if (iconId == null) return null

        return String.format(OPEN_WEATHER_ICONS_URL_FORMAT, iconId)
    }
}