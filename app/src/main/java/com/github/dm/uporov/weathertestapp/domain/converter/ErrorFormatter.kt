package com.github.dm.uporov.weathertestapp.domain.converter

import android.content.Context
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.api.exception.ServerErrorException
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationFetchingException
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationPermissionDeniedException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface ErrorFormatter {

    fun format(error: Throwable?): String
}

@Singleton
class ErrorFormatterImpl @Inject constructor(
    @ApplicationContext context: Context
) : ErrorFormatter {

    private val locationPermissionDenied: String =
        context.resources.getString(R.string.error_location_permission_denied)
    private val serverError: String =
        context.resources.getString(R.string.error_server)
    private val locationFetchingError: String =
        context.resources.getString(R.string.error_location_fetching)
    private val otherError: String =
        context.resources.getString(R.string.error_other)


    override fun format(error: Throwable?) = when (error) {
        is LocationPermissionDeniedException -> locationPermissionDenied
        is ServerErrorException -> serverError
        is LocationFetchingException -> locationFetchingError
        else -> otherError
    }
}

