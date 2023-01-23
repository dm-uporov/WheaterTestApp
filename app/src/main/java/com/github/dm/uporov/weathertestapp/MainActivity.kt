package com.github.dm.uporov.weathertestapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.dm.uporov.weathertestapp.ui.main_screen.MainFragment
import com.github.dm.uporov.weathertestapp.ui.permission_screen.OnPermissionGrantedCallback
import com.github.dm.uporov.weathertestapp.ui.permission_screen.PermissionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnPermissionGrantedCallback {

    private var hasPermission: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            hasPermission = isPermissionGranted()
            navigate()
        }
    }

    override fun onStart() {
        super.onStart()
        val hadPermission = hasPermission
        hasPermission = isPermissionGranted()
        // this is possible when user is returning from settings app
        if (hasPermission != hadPermission) {
            navigate()
        }
    }

    private fun navigate() {
        if (hasPermission) {
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

    override fun onPermissionGranted() {
        showFragment(MainFragment.newInstance())
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}