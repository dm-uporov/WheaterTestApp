package com.github.dm.uporov.weathertestapp.api.model

import com.google.gson.annotations.SerializedName


data class Clouds(
    @SerializedName("all") var all: Int? = null
)