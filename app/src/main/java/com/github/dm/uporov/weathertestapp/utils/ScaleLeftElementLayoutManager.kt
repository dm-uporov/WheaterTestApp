package com.github.dm.uporov.weathertestapp.utils

import android.content.Context
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs


/**
 * This layout manager scales the very left element
 *  **/
class ScaleLeftElementLayoutManager(
    context: Context,
    @DimenRes elementWidth: Int,
    private val minScale: Float = 1f,
    private val maxScale: Float = 1.2f,
) : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {

    private val leftScalePointX: Int
    private val rightScalePointX: Int

    private val helper: OrientationHelper = OrientationHelper.createHorizontalHelper(this)

    init {
        val elementWidthPx = context.resources.getDimensionPixelSize(elementWidth)
        leftScalePointX = -(elementWidthPx / 4)
        rightScalePointX = elementWidthPx
    }

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
