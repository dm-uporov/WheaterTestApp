package com.github.dm.uporov.weathertestapp.ui.main_screen.items

import android.view.View
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastShortItem

interface OnForecastItemClickListener {

    fun onForecastItemClicked(item: ForecastShortItem, position: Int, clickedView: View)
}