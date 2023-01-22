package com.github.dm.uporov.weathertestapp.ui.items

import android.view.View
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem

interface OnForecastItemClickListener {

    fun onForecastItemClicked(item: ForecastShortItem, position: Int, clickedView: View)
}