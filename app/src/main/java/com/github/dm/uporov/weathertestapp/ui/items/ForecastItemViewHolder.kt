package com.github.dm.uporov.weathertestapp.ui.items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.dm.uporov.weathertestapp.R

class ForecastItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dateView: TextView
    private val iconView: ImageView
    private val dayTemperatureView: TextView
    private val nightTemperatureView: TextView

    init {
        dateView = itemView.findViewById(R.id.date)
        iconView = itemView.findViewById(R.id.icon)
        dayTemperatureView = itemView.findViewById(R.id.day_temperature)
        nightTemperatureView = itemView.findViewById(R.id.night_temperature)
    }

    fun bindDate(date: String) {
        dateView.text = date
    }

    fun bindIconView(iconUrl: String?) {
        iconView.load(iconUrl) {
            placeholder(R.drawable.ic_placeholder)
        }
    }

    fun bindDayTemperatureView(dayTemperature: String) {
        dayTemperatureView.text = dayTemperature
    }

    fun bindNightTemperatureView(nightTemperature: String) {
        nightTemperatureView.text = nightTemperature
    }

    fun setOnClickListener(onClick: (() -> Unit)?) {
        if (onClick == null) {
            itemView.setOnClickListener(null)
        } else {
            itemView.setOnClickListener { onClick.invoke() }
        }
    }
}