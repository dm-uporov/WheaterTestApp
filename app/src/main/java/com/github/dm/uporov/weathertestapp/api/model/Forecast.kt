package com.github.dm.uporov.weathertestapp.api.model

import com.google.gson.annotations.SerializedName


data class Forecast(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var mainInformation: MainInformation?,
    @SerializedName("weather") var weather: ArrayList<Weather>,
    @SerializedName("clouds") var clouds: Clouds?,
    @SerializedName("wind") var wind: Wind?,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Float? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("dt_txt") var dtTxt: String? = null
)