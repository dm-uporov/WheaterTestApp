package com.github.dm.uporov.weathertestapp.ui.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.SingleActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface CurrentWeatherNotificationManager {

    fun showNotification(title: String, text: String)

    fun hideNotification()
}

private const val NOTIFICATION_ID = 3121993
private const val MAIN_CHANNEL_ID = "main"

@Singleton
class CurrentWeatherNotificationManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CurrentWeatherNotificationManager {

    private val channelName: String =
        context.resources.getString(R.string.notification_channel_name)

    override fun showNotification(title: String, text: String) {
        createNotification(title, text)
    }

    override fun hideNotification() {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
    }

    private fun createNotification(title: String, text: String) {
        val notificationIntent = Intent(context, SingleActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notification: Notification = NotificationCompat.Builder(context, MAIN_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            MAIN_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_LOW
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        channel.importance = NotificationManager.IMPORTANCE_DEFAULT
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}