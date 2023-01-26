package com.github.dm.uporov.weathertestapp.ui.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.dm.uporov.weathertestapp.domain.repository.CurrentWeatherRepository
import com.github.dm.uporov.weathertestapp.domain.converter.TemperatureFormatter
import com.github.dm.uporov.weathertestapp.utils.capitalize
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

private const val NOTIFICATION_FULL_TITLE_FORMAT = "%s: %s"
private const val NOTIFICATION_SHORT_TITLE_FORMAT = "Weather: %s"

@HiltWorker
class RefreshWeatherForecastWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: CurrentWeatherRepository,
    private val notificationManager: CurrentWeatherNotificationManager,
    private val temperatureFormatter: TemperatureFormatter,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val weather = try {
            repository.getCurrentWeather()
        } catch (e: Throwable) {
            return Result.failure()
        } ?: return Result.failure()

        val temperature = temperatureFormatter.format(weather.detailedInfo.temp)

        val title = weather.cityName?.let {
            String.format(NOTIFICATION_FULL_TITLE_FORMAT, it, temperature)
        } ?: String.format(NOTIFICATION_SHORT_TITLE_FORMAT, temperature)

        val text = weather.weather.first().description?.capitalize() ?: ""

        notificationManager.showNotification(title, text)

        return Result.success()
    }
}