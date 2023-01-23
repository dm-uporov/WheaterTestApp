package com.github.dm.uporov.weathertestapp.utils

import android.view.View
import android.widget.TextView

fun TextView.bindText(value: String?) {
    if (value == null) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        text = value
    }
}