package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val threeHoursVolumeMm: Double?
)