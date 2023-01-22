package com.github.dm.uporov.weathertestapp.utils

import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class SnapPositionScrollListener @Inject constructor(
    private val snapHelper: LeftBorderSnapHelper
) : RecyclerView.OnScrollListener() {

    private var snapPosition = RecyclerView.NO_POSITION

    var onSnapPositionChangedListener: ((Int) -> Unit)? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        maybeNotifySnapPositionChange(recyclerView)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
             maybeNotifySnapPositionChange(recyclerView)
        }
    }

    private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            this.snapPosition = snapPosition
            onSnapPositionChangedListener?.invoke(snapPosition)
        }
    }
}