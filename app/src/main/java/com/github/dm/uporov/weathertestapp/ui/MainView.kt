package com.github.dm.uporov.weathertestapp.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.ui.items.ForecastItemsAdapter
import com.github.dm.uporov.weathertestapp.ui.items.OnForecastItemClickListener
import com.github.dm.uporov.weathertestapp.ui.model.ForecastDetailedItem
import com.github.dm.uporov.weathertestapp.ui.model.ForecastShortItem
import com.github.dm.uporov.weathertestapp.utils.LeftBorderSnapHelper
import com.github.dm.uporov.weathertestapp.utils.SnapPositionScrollListener
import com.github.dm.uporov.weathertestapp.utils.bindText

interface MainView {

    fun showLoading()

    fun hideLoading()

    fun bindDetails(details: ForecastDetailedItem?)

    fun onDestroy()
}

class MainViewImpl(
    view: View,
    val adapter: ForecastItemsAdapter,
    val snapHelper: LeftBorderSnapHelper,
    val snapPositionScrollListener: SnapPositionScrollListener,
) : MainView, OnForecastItemClickListener {

    private val contentView: View
    private val loadingView: ShimmerFrameLayout

    private val recyclerView: RecyclerView
    private val location: TextView
    private val locationIcon: View
    private val date: TextView
    private val dateIcon: View
    private val temperature: TextView
    private val weatherIcon: ImageView
    private val feelsLike: TextView
    private val pressure: TextView
    private val pressureIcon: View
    private val humidity: TextView
    private val humidityIcon: View
    private val visibility: TextView
    private val visibilityIcon: View
    private val precipitation: TextView
    private val precipitationIcon: View

    private val feelsLikeFormat: String
    private val pressureFormat: String
    private val humidityFormat: String
    private val visibilityFormat: String
    private val precipitationFormat: String

    init {
        contentView = view.findViewById(R.id.content)
        loadingView = view.findViewById(R.id.shimmer_view_container)

        recyclerView = view.findViewById(R.id.forecasts_recycler)
        recyclerView.adapter = adapter
        adapter.onForecastItemClickListener = this
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(snapPositionScrollListener)

        location = view.findViewById(R.id.location)
        locationIcon = view.findViewById(R.id.location_icon)
        date = view.findViewById(R.id.date_time)
        dateIcon = view.findViewById(R.id.time_icon)
        temperature = view.findViewById(R.id.temperature)
        weatherIcon = view.findViewById(R.id.icon)
        feelsLike = view.findViewById(R.id.feels_like)
        pressure = view.findViewById(R.id.pressure)
        pressureIcon = view.findViewById(R.id.pressure_icon)
        humidity = view.findViewById(R.id.humidity)
        humidityIcon = view.findViewById(R.id.humidity_icon)
        visibility = view.findViewById(R.id.visibility)
        visibilityIcon = view.findViewById(R.id.visibility_icon)
        precipitation = view.findViewById(R.id.precipitation)
        precipitationIcon = view.findViewById(R.id.precipitation_icon)

        feelsLikeFormat = view.context.resources.getString(R.string.feels_like_format)
        pressureFormat = view.context.resources.getString(R.string.pressure_format)
        humidityFormat = view.context.resources.getString(R.string.humidity_format)
        visibilityFormat = view.context.resources.getString(R.string.visibility_format)
        precipitationFormat = view.context.resources.getString(R.string.precipitation_format)
    }

    override fun showLoading() {
        contentView.isVisible = false
        loadingView.isVisible = true
        loadingView.startShimmer()
    }

    override fun hideLoading() {
        contentView.isVisible = true
        loadingView.isVisible = false
        loadingView.stopShimmer()
    }

    override fun bindDetails(details: ForecastDetailedItem?) {
        location.bindTextWithIcon(details?.city, locationIcon)
        date.bindTextWithIcon(details?.date, dateIcon)

        temperature.bindText(details?.temperature)
        weatherIcon.load(details?.iconUrl) {
            placeholder(R.drawable.ic_placeholder)
        }

        val formattedFeelsLike = details?.feelsLike?.let { String.format(feelsLikeFormat, it) }
        feelsLike.bindText(formattedFeelsLike)

        pressure.bindFormattedTextWithIcon(details?.pressure, pressureFormat, pressureIcon)
        humidity.bindFormattedTextWithIcon(details?.humidity, humidityFormat, humidityIcon)
        visibility.bindFormattedTextWithIcon(details?.visibility, visibilityFormat, visibilityIcon)
        precipitation.bindFormattedTextWithIcon(
            details?.probabilityOfPrecipitation,
            precipitationFormat,
            precipitationIcon
        )
    }

    private fun TextView.bindFormattedTextWithIcon(text: String?, formatter: String, icon: View) {
        bindTextWithIcon(text.let { String.format(formatter, it) }, icon)
    }

    private fun TextView.bindTextWithIcon(text: String?, icon: View) {
        bindText(text)
        icon.isVisible = text != null
    }

    override fun onDestroy() {
        adapter.onForecastItemClickListener = null
        snapHelper.attachToRecyclerView(null)
    }

    override fun onForecastItemClicked(item: ForecastShortItem, position: Int, clickedView: View) {
        snapHelper.snapToView(clickedView, recyclerView)
    }
}