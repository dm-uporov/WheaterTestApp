package com.github.dm.uporov.weathertestapp.ui.items

import androidx.recyclerview.widget.DiffUtil
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ForecastShortItemDiffCallback @Inject constructor() :
    DiffUtil.ItemCallback<ForecastShortItem>() {

    override fun areItemsTheSame(
        oldItem: ForecastShortItem,
        newItem: ForecastShortItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ForecastShortItem,
        newItem: ForecastShortItem
    ): Boolean {
        return oldItem == newItem
    }
}