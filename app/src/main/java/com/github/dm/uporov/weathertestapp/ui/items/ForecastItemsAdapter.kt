package com.github.dm.uporov.weathertestapp.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ForecastItemsAdapter @Inject constructor(
    diffCallback: ForecastShortItemDiffCallback
) : ListAdapter<ForecastShortItem, ForecastItemViewHolder>(diffCallback) {

    var onForecastItemClickListener: OnForecastItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItemViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
            .let(::ForecastItemViewHolder)
    }

    override fun onBindViewHolder(holder: ForecastItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindDate(item.date)
        holder.bindTime(item.time)
        holder.bindIcon(item.iconUrl)
        holder.bindTemperature(item.temperature)
        holder.setOnClickListener {
            onForecastItemClickListener?.onForecastItemClicked(
                item = item,
                position = position,
                clickedView = holder.itemView
            )
        }
    }
}