package com.github.dm.uporov.weathertestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.dm.uporov.weathertestapp.domain.repository.ConnectivityMonitor
import com.github.dm.uporov.weathertestapp.domain.repository.GrantedPermissionsRepository
import com.github.dm.uporov.weathertestapp.ui.main_screen.MainFragment
import com.github.dm.uporov.weathertestapp.ui.permission_screen.OnImportantPermissionGrantedCallback
import com.github.dm.uporov.weathertestapp.ui.permission_screen.PermissionFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SingleActivity : AppCompatActivity(), OnImportantPermissionGrantedCallback {

    private lateinit var viewModel: ActivityViewModel

    @Inject
    lateinit var grantedPermissionsRepository: GrantedPermissionsRepository
    @Inject
    lateinit var connectivityMonitor: ConnectivityMonitor

    private var hasLocationPermission: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hasLocationPermission = grantedPermissionsRepository.isLocationPermissionGranted
        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]

        if (savedInstanceState == null) {
            navigate(hasLocationPermission)
        }
    }

    override fun onStart() {
        super.onStart()
        val hadPermission = hasLocationPermission
        hasLocationPermission = grantedPermissionsRepository.isLocationPermissionGranted
        // this is possible when user is returning from settings app
        if (hasLocationPermission != hadPermission) {
            navigate(hasLocationPermission)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onApplicationResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onApplicationPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityMonitor.stopMonitoring()
    }

    private fun navigate(hasLocationPermission: Boolean) {
        if (hasLocationPermission) {
            showFragment(MainFragment.newInstance())
        } else {
            showFragment(PermissionFragment.newInstance())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onAllImportantPermissionsGranted() {
        showFragment(MainFragment.newInstance())
    }
}