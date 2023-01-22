package com.github.dm.uporov.weathertestapp.ui

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
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
    private val temperature: TextView

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
        temperature = view.findViewById(R.id.temperature)
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
        location.bindText(details?.city)
        locationIcon.isVisible = details?.city != null
        temperature.bindText(details?.temperature)
    }

    override fun onDestroy() {
        adapter.onForecastItemClickListener = null
        snapHelper.attachToRecyclerView(null)
    }

    override fun onForecastItemClicked(item: ForecastShortItem, position: Int, clickedView: View) {
        snapHelper.snapToView(clickedView, recyclerView)
    }
}