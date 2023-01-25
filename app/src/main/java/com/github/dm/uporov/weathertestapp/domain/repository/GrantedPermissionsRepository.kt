package com.github.dm.uporov.weathertestapp.domain.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface GrantedPermissionsRepository {
    val isLocationPermissionGranted: Boolean
    val isNotificationPermissionGranted: Boolean
}

@Singleton
class GrantedPermissionsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GrantedPermissionsRepository {

    override val isLocationPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    override val isNotificationPermissionGranted: Boolean
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        }
}