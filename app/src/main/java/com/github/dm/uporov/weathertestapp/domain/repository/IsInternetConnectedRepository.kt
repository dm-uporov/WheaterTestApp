package com.github.dm.uporov.weathertestapp.domain.repository

import android.content.Context
import android.net.*
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface ConnectivityMonitor {

    fun startMonitoring()

    fun stopMonitoring()
}

interface IsInternetConnectedRepository {

    val isInternetConnected: Boolean
}

@Singleton
class IsInternetConnectedRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ConnectivityManager.NetworkCallback(), ConnectivityMonitor, IsInternetConnectedRepository {

    private var _isInternetConnected = true

    override val isInternetConnected: Boolean
        get() = _isInternetConnected

    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    init {
        startMonitoring()
    }

    override fun onAvailable(network: Network) {
        connectivityManager?.getNetworkCapabilities(network)?.let(::updateConnectionStatus)
            ?: run { _isInternetConnected = true }
    }

    override fun onLost(network: Network) {
        _isInternetConnected = false
    }

    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        updateConnectionStatus(networkCapabilities = networkCapabilities)
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {}

    override fun startMonitoring() {
        connectivityManager?.registerNetworkCallback(NetworkRequest.Builder().build(), this)
    }

    override fun stopMonitoring() {
        connectivityManager?.unregisterNetworkCallback(this)
    }

    private fun updateConnectionStatus(networkCapabilities: NetworkCapabilities) {
        _isInternetConnected =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}