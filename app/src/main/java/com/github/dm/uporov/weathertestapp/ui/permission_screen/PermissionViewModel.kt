package com.github.dm.uporov.weathertestapp.ui.permission_screen

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.repository.PermissionDenialCountRepository
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.ForecastDetailedItem
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.MainUiState
import com.github.dm.uporov.weathertestapp.ui.permission_screen.model.PermissionNotGrantedUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MAX_DENIAL_COUNT = 2

@HiltViewModel
class PermissionIsNotGrantedViewModel @Inject constructor(
    private val permissionDenialCountRepository: PermissionDenialCountRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PermissionNotGrantedUIState> =
        MutableStateFlow(PermissionNotGrantedUIState.CanAskForPermissionUIState)
    val uiState: StateFlow<PermissionNotGrantedUIState> = _uiState.asStateFlow()

    fun onStart(shouldShowRequestPermissionRationale: Boolean) {
        updateState(shouldShowRequestPermissionRationale)
    }

    fun onPermissionDenied(shouldShowRequestPermissionRationale: Boolean) {
        // Did user deny permission for the first time?
        if ((permissionDenialCountRepository.permissionDenialCount == 0 && shouldShowRequestPermissionRationale) ||
            // Or did user deny permission for the second time?
            (permissionDenialCountRepository.permissionDenialCount == 1 && !shouldShowRequestPermissionRationale)
        ) {
            permissionDenialCountRepository.increasePermissionDenials()
        }
        updateState(shouldShowRequestPermissionRationale)
    }


    private fun updateState(shouldShowRequestPermissionRationale: Boolean) {
        _uiState.update {
            when {
                shouldShowRequestPermissionRationale -> PermissionNotGrantedUIState.CanAskForPermissionUIState
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionDenialCountRepository.permissionDenialCount == MAX_DENIAL_COUNT ->
                    PermissionNotGrantedUIState.NeedToGoToSettingsUIState
                else -> PermissionNotGrantedUIState.CanAskForPermissionUIState
            }
        }
    }
}