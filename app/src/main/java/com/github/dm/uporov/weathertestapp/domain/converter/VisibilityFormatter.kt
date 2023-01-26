package com.github.dm.uporov.weathertestapp.domain.converter

import android.content.res.Resources
import com.github.dm.uporov.weathertestapp.R
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


interface VisibilityFormatter {

    fun format(visibility: Int?): String?
}

private const val VISIBILITY_FORMAT = "%dm"
private const val MAX_VISIBILITY = 10_000

@ViewModelScoped
class VisibilityFormatterImpl @Inject constructor(
    resources: Resources
) : VisibilityFormatter {

    private val maxVisibilityText: String = resources.getString(R.string.max_visibility)

    override fun format(visibility: Int?): String? {
        if (visibility == null) return null

        return if (visibility >= MAX_VISIBILITY) {
            maxVisibilityText
        } else {
            String.format(VISIBILITY_FORMAT, visibility)
        }
    }
}