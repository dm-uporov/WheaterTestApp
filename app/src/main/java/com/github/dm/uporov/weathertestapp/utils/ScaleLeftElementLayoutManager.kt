package com.github.dm.uporov.weathertestapp.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs


class ScaleLeftElementLayoutManager : LinearLayoutManager {

    private val minScale = 1f
    private val maxScale = 1.2f
    private val leftScalePointX = (-20).px
    private val rightScalePointX = 50.px

    private val helper: OrientationHelper = OrientationHelper.createHorizontalHelper(this)

    constructor(context: Context?) : super(context)

    constructor(
        context: Context?, @RecyclerView.Orientation orientation: Int,
        reverseLayout: Boolean
    ) : super(context, orientation, reverseLayout)

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChild()
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler?,
        state: RecyclerView.State?
    ): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            scaleChild()
            super.scrollHorizontallyBy(dx, recycler, state)
        } else {
            0
        }
    }

    private fun scaleChild() {
        for (i in 0 until childCount) {
            val child: View = getChildAt(i) ?: continue

            val childLeftEdge = getDecoratedLeft(child)
            val scale = computeScaleForPoint(childLeftEdge)
            child.scaleY = scale
            child.scaleX = scale
        }
    }

    private fun computeScaleForPoint(point: Int): Float {
        val leftEdge = helper.startAfterPadding
        return if (point in (leftScalePointX + 1) until rightScalePointX) {
            val deltaRelative: Float =
                if (point in (leftScalePointX + 1) until leftEdge) {
                    abs((leftEdge - point.toFloat()) / (leftEdge - leftScalePointX))
                } else {
                    abs((point.toFloat() - leftEdge) / (rightScalePointX - leftEdge))
                }
            val deltaScale = (maxScale - minScale) * (1f - deltaRelative)
            minScale + deltaScale
        } else {
            minScale
        }
    }
}
