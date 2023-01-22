package com.github.dm.uporov.weathertestapp.ui.items

import android.view.View

interface OnForecastItemClickListener {

    fun onForecastItemClicked(item: ForecastItem, position: Int, clickedView: View)
}