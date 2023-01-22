package com.github.dm.uporov.weathertestapp.ui.items

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem
import javax.inject.Inject

class ForecastItemsAdapter @Inject constructor(
    private val onForecastItemClickListener: OnForecastItemClickListener
) : RecyclerView.Adapter<ForecastItemViewHolder>() {

    private val items = mutableListOf<ForecastShortItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<ForecastShortItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItemViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
            .let(::ForecastItemViewHolder)
    }

    override fun onBindViewHolder(holder: ForecastItemViewHolder, position: Int) {
        val item = items[position]
        holder.bindDate(item.dateTitle)
        holder.bindIconView(item.iconUrl)
        holder.bindDayTemperatureView(item.dayTemperature)
        holder.bindNightTemperatureView(item.nightTemperature)
        holder.setOnClickListener {
            onForecastItemClickListener.onForecastItemClicked(items[position], position, holder.itemView)
        }
    }

    override fun getItemCount(): Int = items.count()
}