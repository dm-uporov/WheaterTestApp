package com.github.dm.uporov.weathertestapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.work.*
import com.github.dm.uporov.weathertestapp.repository.CurrentWeatherRepository
import com.github.dm.uporov.weathertestapp.repository.CurrentWeatherRepositoryImpl
import com.github.dm.uporov.weathertestapp.repository.GrantedPermissionsRepository
import com.github.dm.uporov.weathertestapp.repository.GrantedPermissionsRepositoryImpl
import com.github.dm.uporov.weathertestapp.repository.converter.TemperatureFormatter
import com.github.dm.uporov.weathertestapp.repository.converter.TemperatureFormatterImpl
import com.github.dm.uporov.weathertestapp.ui.notification.CurrentWeatherNotificationManager
import com.github.dm.uporov.weathertestapp.ui.notification.CurrentWeatherNotificationManagerImpl
import com.github.dm.uporov.weathertestapp.ui.notification.RefreshWeatherForecastWorker
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ApplicationModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val SHARED_PREFERENCES_NAME = "weather_app_preferences"

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

    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {

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
    }
}