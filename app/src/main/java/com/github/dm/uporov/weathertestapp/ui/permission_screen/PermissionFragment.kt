package com.github.dm.uporov.weathertestapp.ui.permission_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.ui.permission_screen.model.PermissionNotGrantedUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

interface OnImportantPermissionGrantedCallback {
    fun onAllImportantPermissionsGranted()
}

@AndroidEntryPoint
class PermissionFragment : Fragment() {

    companion object {
        fun newInstance() = PermissionFragment()
    }

    private lateinit var viewModel: PermissionIsNotGrantedViewModel

    private lateinit var onImportantPermissionGrantedCallback: OnImportantPermissionGrantedCallback
    private lateinit var description: TextView
    private lateinit var grantButton: Button
    private lateinit var openSettingsButton: Button

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsGranted ->
        if (permissionsGranted[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            onImportantPermissionGrantedCallback.onAllImportantPermissionsGranted()
        } else {
            viewModel.onLocationPermissionDenied(
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnImportantPermissionGrantedCallback) {
            onImportantPermissionGrantedCallback = context
        } else {
            throw RuntimeException("${context.javaClass} should implement ${OnImportantPermissionGrantedCallback::class.java}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PermissionIsNotGrantedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_permission_is_not_granted, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        description = view.findViewById(R.id.description)
        grantButton = view.findViewById(R.id.grant_button)
        openSettingsButton = view.findViewById(R.id.open_settings_button)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::applyUiState)
            }
        }
    }

    private fun applyUiState(state: PermissionNotGrantedUIState) {
        description.text = resources.getString(state.descriptionRes)
        grantButton.isVisible = state.showGrantButton
        openSettingsButton.isVisible = state.showSettingsButton
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    override fun onResume() {
        super.onResume()
        grantButton.setOnClickListener { requestPermission() }
        openSettingsButton.setOnClickListener { openSettingsApp() }
    }

    override fun onPause() {
        super.onPause()
        grantButton.setOnClickListener(null)
        openSettingsButton.setOnClickListener(null)
    }

    private fun requestPermission() {
        val permissions = if (Build.VERSION.SDK_INT >= TIRAMISU) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS,
            )
        } else {
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        locationPermissionRequest.launch(permissions)
    }

    private fun openSettingsApp() {
        Intent().run {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", context?.packageName, null)
            startActivity(this)
        }
    }
}