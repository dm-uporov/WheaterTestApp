package com.github.dm.uporov.weathertestapp

import androidx.lifecycle.ViewModel
import androidx.work.*
import com.github.dm.uporov.weathertestapp.domain.repository.GrantedPermissionsRepository
import com.github.dm.uporov.weathertestapp.ui.notification.CurrentWeatherNotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val WORK_REQUEST_NAME = "refresh_weather_forecast"

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val workRequest: PeriodicWorkRequest,
    private val grantedPermissionsRepository: GrantedPermissionsRepository,
    private val notificationManager: CurrentWeatherNotificationManager,
) : ViewModel() {

    fun onApplicationResume() {
        workManager.cancelUniqueWork(WORK_REQUEST_NAME)
        notificationManager.hideNotification()
    }

    fun onApplicationPause() {
        if (grantedPermissionsRepository.isLocationPermissionGranted && grantedPermissionsRepository.isNotificationPermissionGranted) {
            workManager.enqueueUniquePeriodicWork(
                WORK_REQUEST_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}