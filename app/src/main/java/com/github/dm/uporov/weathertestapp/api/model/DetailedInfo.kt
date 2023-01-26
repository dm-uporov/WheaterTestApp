package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class DetailedInfo(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double? = null,
    @SerializedName("temp_min")
    val tempMin: Double? = null,
    @SerializedName("temp_max")
    val tempMax: Double? = null,
    @SerializedName("pressure")
    val pressurePHa: Int? = null,
    @SerializedName("sea_level")
    val seaLevel: Int? = null,
    @SerializedName("grnd_level")
    val grndLevel: Int? = null,
    @SerializedName("humidity")
    val humidityPercents: Int? = null,
    @SerializedName("temp_kf")
    val tempKf: Double? = null
)