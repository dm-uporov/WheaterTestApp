package com.github.dm.uporov.weathertestapp.utils

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlin.math.abs

class LeftBorderSnapHelper @Inject constructor() : LinearSnapHelper() {

    private var orientationHelper: OrientationHelper? = null

    private fun getHorizontalHelper(
        layoutManager: RecyclerView.LayoutManager
    ): OrientationHelper {
        if (orientationHelper == null || orientationHelper?.layoutManager !== layoutManager) {
            orientationHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return orientationHelper!!
    }

    fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val resultArray = IntArray(2)
        resultArray[0] = distanceToLeftBorder(targetView, getHorizontalHelper(layoutManager))
        return resultArray
    }

    private fun distanceToLeftBorder(targetView: View, helper: OrientationHelper): Int {
        val childStart = helper.getDecoratedStart(targetView)
        val containerStart = helper.startAfterPadding
        return childStart - containerStart - 10
    }


    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return findLeftView(layoutManager, getHorizontalHelper(layoutManager))
    }

    /**
     * Return the child view that is currently closest to the left border of this parent.
     *
     * @param layoutManager The [RecyclerView.LayoutManager] associated with the attached
     * [RecyclerView].
     * @param helper The relevant [OrientationHelper] for the attached [RecyclerView].
     *
     * @return the child view that is currently closest to the center of this parent.
     */
    private fun findLeftView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return null
        }
        var closestChild: View? = null
        val left = helper.startAfterPadding
        var absClosest = Int.MAX_VALUE
        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            val childLeft = helper.getDecoratedStart(child)
            val absDistance = abs(childLeft - left)
            /** if child left edge is closer than previous closest, set it as closest   */
            if (absDistance < absClosest) {
                absClosest = absDistance
                closestChild = child
            }
        }
        return closestChild
    }
}
