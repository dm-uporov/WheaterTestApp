package com.github.dm.uporov.weathertestapp.ui.items

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.dm.uporov.weathertestapp.R
import javax.inject.Inject

class ForecastItemsAdapter @Inject constructor() : RecyclerView.Adapter<ForecastItemViewHolder>() {

    private val items = mutableListOf<ForecastItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<ForecastItem>) {
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
        holder.bindTitle(item.dateTitle)
    }

    override fun getItemCount(): Int = items.count()
}