package com.github.dm.uporov.weathertestapp.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.work.*
import com.github.dm.uporov.weathertestapp.domain.converter.ErrorFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.ErrorFormatterImpl
import com.github.dm.uporov.weathertestapp.domain.converter.TemperatureFormatter
import com.github.dm.uporov.weathertestapp.domain.converter.TemperatureFormatterImpl
import com.github.dm.uporov.weathertestapp.domain.repository.*
import com.github.dm.uporov.weathertestapp.ui.notification.CurrentWeatherNotificationManager
import com.github.dm.uporov.weathertestapp.ui.notification.CurrentWeatherNotificationManagerImpl
import com.github.dm.uporov.weathertestapp.ui.notification.RefreshWeatherForecastWorker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [ApplicationModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val SHARED_PREFERENCES_NAME = "weather_app_preferences"

    @Singleton
    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<RefreshWeatherForecastWorker>(
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideLocationService(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    @ApplicationCoroutineScope
    fun provideApplicationCoroutineScope(): CoroutineScope {
        return MainScope()
    }

    @Singleton
    @Provides
    @IoCoroutineDispatcher
    fun provideIoCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {

        @Singleton
        @Binds
        fun bindIsInternetConnectedRepository(repository: IsInternetConnectedRepositoryImpl): IsInternetConnectedRepository

        @Singleton
        @Binds
        fun bindConnectivityMonitor(repository: IsInternetConnectedRepositoryImpl): ConnectivityMonitor

        @Singleton
        @Binds
        fun bindGrantedPermissionsRepository(repository: GrantedPermissionsRepositoryImpl): GrantedPermissionsRepository

        @Singleton
        @Binds
        fun bindCurrentWeatherRepository(repository: CurrentWeatherRepositoryImpl): CurrentWeatherRepository

        @Singleton
        @Binds
        fun bindCurrentWeatherNotificationManager(manager: CurrentWeatherNotificationManagerImpl): CurrentWeatherNotificationManager

        @Singleton
        @Binds
        fun bindTemperatureConverter(formatter: TemperatureFormatterImpl): TemperatureFormatter

        @Singleton
        @Binds
        fun bindLocationRepository(repository: LocationRepositoryImpl): LocationRepository

        @Singleton
        @Binds
        fun bindErrorFormatter(errorFormatter: ErrorFormatterImpl): ErrorFormatter
    }
}

@Qualifier
annotation class ApplicationCoroutineScope

@Qualifier
annotation class IoCoroutineDispatcher
