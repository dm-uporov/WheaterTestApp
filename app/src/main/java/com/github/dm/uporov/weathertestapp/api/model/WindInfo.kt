package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class WindInfo(
    @SerializedName("speed")
    val speed: Double?,
    @SerializedName("deg")
    val deg: Int?,
    @SerializedName("gust")
    val gust: Double?
)