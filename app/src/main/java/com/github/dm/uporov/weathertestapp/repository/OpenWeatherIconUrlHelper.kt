package com.github.dm.uporov.weathertestapp.repository

import javax.inject.Inject

interface OpenWeatherIconUrlHelper {

    fun createUrl(iconId: String?): String?
}

private const val OPEN_WEATHER_ICONS_URL_FORMAT = "https://openweathermap.org/img/wn/%s@2x.png"

class OpenWeatherIconUrlHelperImpl @Inject constructor() : OpenWeatherIconUrlHelper {

    override fun createUrl(iconId: String?): String? {
        if (iconId == null) return null

        return String.format(OPEN_WEATHER_ICONS_URL_FORMAT, iconId)
    }
}