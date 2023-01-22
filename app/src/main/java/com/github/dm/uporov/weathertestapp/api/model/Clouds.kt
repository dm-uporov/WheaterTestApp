package com.github.dm.uporov.weathertestapp.api.model


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int?
)