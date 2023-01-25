package com.github.dm.uporov.weathertestapp.domain.repository

import android.content.SharedPreferences
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface PermissionDenialCountRepository {

    val permissionDenialCount: Int

    fun increasePermissionDenials()
}

private const val PERMISSION_DENIAL_COUNT_KEY = "permission_denials_count"

@ViewModelScoped
class PermissionDenialCountRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PermissionDenialCountRepository {

    private var _permissionDenialCount: Int =
        sharedPreferences.getInt(PERMISSION_DENIAL_COUNT_KEY, 0)

    override val permissionDenialCount: Int
        get() = _permissionDenialCount

    override fun increasePermissionDenials() {
        _permissionDenialCount++
        sharedPreferences.edit()
            .putInt(PERMISSION_DENIAL_COUNT_KEY, _permissionDenialCount)
            .apply()
    }
}