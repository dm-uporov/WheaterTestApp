package com.github.dm.uporov.weathertestapp.domain.repository

import android.annotation.SuppressLint
import android.location.Location
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationFetchingException
import com.github.dm.uporov.weathertestapp.domain.exceprion.LocationPermissionDeniedException
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface LocationRepository {

    @Throws(LocationPermissionDeniedException::class, LocationFetchingException::class)
    suspend fun getLastLocation(): Location?

    @Throws(LocationPermissionDeniedException::class, LocationFetchingException::class)
    suspend fun getCurrentLocation(): Location?
}

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val locationService: FusedLocationProviderClient,
    private val permissionsRepository: GrantedPermissionsRepository,
) : LocationRepository {

    private var lastKnownLocation: Location? = null

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Location? {
        val lastLocation = lastKnownLocation
        if (lastLocation != null) return lastLocation

        return suspendCoroutine { continuation ->
            if (!permissionsRepository.isLocationPermissionGranted) {
                continuation.resumeWithException(LocationPermissionDeniedException())
                return@suspendCoroutine
            }

            with(locationService.lastLocation) {
                addOnSuccessListener {
                    lastKnownLocation = it
                    continuation.resume(it)
                }
                addOnCanceledListener {
                    continuation.resumeWithException(LocationFetchingException())
                }
                addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        return suspendCoroutine { continuation ->
            if (!permissionsRepository.isLocationPermissionGranted) {
                continuation.resumeWithException(LocationPermissionDeniedException())
                return@suspendCoroutine
            }

            with(
                locationService.getCurrentLocation(CurrentLocationRequest.Builder().build(), null)
            ) {
                addOnSuccessListener {
                    lastKnownLocation = it
                    continuation.resume(it)
                }
                addOnCanceledListener {
                    continuation.resumeWithException(LocationFetchingException())
                }
                addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }
        }
    }
}