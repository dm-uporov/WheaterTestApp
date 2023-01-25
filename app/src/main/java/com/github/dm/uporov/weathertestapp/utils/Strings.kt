package com.github.dm.uporov.weathertestapp.utils

import java.util.*


fun String?.capitalize() = this?.replaceFirstChar {
    if (it.isLowerCase()) {
        it.titlecase(Locale.getDefault())
    } else {
        it.toString()
    }
}