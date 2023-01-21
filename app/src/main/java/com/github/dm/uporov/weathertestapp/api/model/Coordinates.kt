package com.github.dm.uporov.weathertestapp.api.model

import com.google.gson.annotations.SerializedName


data class Coordinates(
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null
)