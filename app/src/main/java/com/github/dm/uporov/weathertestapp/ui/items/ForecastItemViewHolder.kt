package com.github.dm.uporov.weathertestapp.ui.items

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.dm.uporov.weathertestapp.R

class ForecastItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView

    init {
        titleTextView = itemView.findViewById(R.id.title)
    }

    fun bindTitle(title: String) {
        titleTextView.text = title
    }

    fun setOnClickListener(onClick: (() -> Unit)?) {
        if (onClick == null) {
            itemView.setOnClickListener(null)
        } else {
            itemView.setOnClickListener { onClick.invoke() }
        }
    }
}