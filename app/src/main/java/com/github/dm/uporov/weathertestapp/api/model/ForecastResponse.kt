package com.github.dm.uporov.weathertestapp.api.model

import com.google.gson.annotations.SerializedName


data class ForecastResponse(
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var forecastsList: ArrayList<Forecast>,
    @SerializedName("city") var city: City?
)