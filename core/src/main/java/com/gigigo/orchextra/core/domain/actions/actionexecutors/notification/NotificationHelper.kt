package com.gigigo.orchextra.core.domain.actions.actionexecutors.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.R.drawable
import com.gigigo.orchextra.core.R.string

class NotificationHelper(private val context: Context) {

    private var notificationManager: NotificationManager? = null

    fun init() {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Orchextra.NOTIFICATION_CHANNEL_LOCATION,
                context.getString(string.app_name),
                NotificationManager.IMPORTANCE_NONE
            )
            channel.setSound(Uri.EMPTY, null)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun getLocationNotification(locationData: NotificationLocationData): Builder? {
        return Builder(context, Orchextra.NOTIFICATION_CHANNEL_LOCATION)
            .setContentTitle(context.getString(string.ox_location_notification_title))
            .setContentText(locationData.locationMessage())
            .setOngoing(true)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setSmallIcon(drawable.ox_notification_alpha_small_icon)
            .setContentIntent(getLocationPendingIntent(locationData.location))
    }

    private fun getLocationPendingIntent(location: Location): PendingIntent? {
        val gmmIntentUri = Uri.parse("geo:${location.latitude},${location.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        return PendingIntent.getActivity(context, 0, mapIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun buildNotificationLocation(notificationLocationData: NotificationLocationData): Notification? {
        return getLocationNotification(notificationLocationData)?.build()
    }

    fun notifyLocation(notificationId: Int, location: Location) {
        val notificationLocationData = NotificationLocationData(location)
        val notification = getLocationNotification(notificationLocationData)
        notification?.setContentText(
            "${NotificationLocationData.locationMessage(location) ?: location}"
        )
        notification?.setContentIntent(getLocationPendingIntent(location))
        notificationManager?.notify(notificationId, notification?.build())
    }

    class NotificationLocationData(val location: Location) {

        fun locationMessage(): String? {
            return locationMessage(this.location)
        }

        companion object {

            fun locationMessage(location: Location): String? {
                val location = location

                val mockMessage =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        if (location.isFromMockProvider) "mock" else "no-mock"
                    } else {
                        "unknown"
                    }
                return "lat ${location.latitude} lng ${location.longitude} $mockMessage "
            }
        }
    }
}