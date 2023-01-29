package com.github.dm.uporov.weathertestapp.domain.repository

import android.content.SharedPreferences
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface PermissionDenialsCountRepository {

    val permissionDenialsCount: Int

    fun increasePermissionDenials()
}

private const val PERMISSION_DENIALS_COUNT_KEY = "permission_denials_count"

@ViewModelScoped
class PermissionDenialsCountRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PermissionDenialsCountRepository {

    private var _permissionDenialCount: Int =
        sharedPreferences.getInt(PERMISSION_DENIALS_COUNT_KEY, 0)

    override val permissionDenialsCount: Int
        get() = _permissionDenialCount

    override fun increasePermissionDenials() {
        _permissionDenialCount++
        sharedPreferences.edit()
            .putInt(PERMISSION_DENIALS_COUNT_KEY, _permissionDenialCount)
            .apply()
    }
}