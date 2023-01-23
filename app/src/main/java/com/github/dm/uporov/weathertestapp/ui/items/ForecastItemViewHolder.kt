package com.github.dm.uporov.weathertestapp.ui.items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.utils.bindText

class ForecastItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dateView: TextView
    private val temperatureView: TextView
    private val iconView: ImageView
    private val timeView: TextView

    init {
        dateView = itemView.findViewById(R.id.date)
        temperatureView = itemView.findViewById(R.id.temperature)
        iconView = itemView.findViewById(R.id.icon)
        timeView = itemView.findViewById(R.id.time)
    }

    fun bindDate(date: String) {
        dateView.text = date
    }

    fun bindTime(time: String) {
        timeView.text = time
    }

    fun bindIcon(iconUrl: String?) {
        iconView.load(iconUrl) {
            placeholder(R.drawable.ic_placeholder)
        }
    }

    fun bindTemperature(temperature: String?) {
        temperatureView.bindText(temperature)
    }

    fun setOnClickListener(onClick: (() -> Unit)?) {
        if (onClick == null) {
            itemView.setOnClickListener(null)
        } else {
            itemView.setOnClickListener { onClick.invoke() }
        }
    }
}