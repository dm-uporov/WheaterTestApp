package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String?
)