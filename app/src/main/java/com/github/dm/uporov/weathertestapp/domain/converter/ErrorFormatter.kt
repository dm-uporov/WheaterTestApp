package com.github.dm.uporov.weathertestapp.domain.converter

import android.content.res.Resources
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.api.exception.NetworkIsNotAvailableException
import com.github.dm.uporov.weathertestapp.api.exception.ServerErrorException
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationFetchingException
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationPermissionDeniedException
import javax.inject.Inject
import javax.inject.Singleton

interface ErrorFormatter {

    fun format(error: Throwable?): String
}

@Singleton
class ErrorFormatterImpl @Inject constructor(
    resources: Resources
) : ErrorFormatter {

    private val locationPermissionDenied: String =
        resources.getString(R.string.error_location_permission_denied)
    private val serverError: String =
        resources.getString(R.string.error_server)
    private val locationFetchingError: String =
        resources.getString(R.string.error_location_fetching)
    private val networkIsNotAvailableError: String =
        resources.getString(R.string.error_network_is_not_available)
    private val otherError: String =
        resources.getString(R.string.error_other)


    override fun format(error: Throwable?) = when (error) {
        is LocationPermissionDeniedException -> locationPermissionDenied
        is ServerErrorException -> serverError
        is LocationFetchingException -> locationFetchingError
        is NetworkIsNotAvailableException -> networkIsNotAvailableError
        else -> otherError
    }
}

