package com.github.dm.uporov.weathertestapp.ui.permission_screen.model

import androidx.annotation.StringRes
import com.github.dm.uporov.weathertestapp.R

sealed class PermissionNotGrantedUIState private constructor(
    @StringRes val descriptionRes: Int,
    val showGrantButton: Boolean,
    val showSettingsButton: Boolean,
) {

    object CanAskForPermissionUIState : PermissionNotGrantedUIState(
        descriptionRes = R.string.permission_required_text,
        showGrantButton = true,
        showSettingsButton = false,
    )

    object NeedToGoToSettingsUIState : PermissionNotGrantedUIState(
        descriptionRes = R.string.permission_required_through_settings_text,
        showGrantButton = false,
        showSettingsButton = true,
    )
}
