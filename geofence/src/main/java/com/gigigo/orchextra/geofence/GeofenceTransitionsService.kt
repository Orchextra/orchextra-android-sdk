/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.geofence

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.GEOFENCE
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.LogUtils.LOGE
import com.gigigo.orchextra.core.utils.LogUtils.LOGI
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent


class GeofenceTransitionsService : Service() {

    private var mChangingConfiguration = false
    private var mServiceHandler: Handler? = null
    private var mNotificationManager: NotificationManager? = null
    private val mBinder = GeofenceBinder()


    override fun onCreate() {
        super.onCreate()

        val handlerThread = HandlerThread(TAG)
        handlerThread.start()
        mServiceHandler = Handler(handlerThread.looper)
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            // Create the channel for the notification
            val channel =
                NotificationChannel(PRIMARY_CHANNEL, name, NotificationManager.IMPORTANCE_LOW)
            channel.lightColor = Color.MAGENTA
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            channel.enableVibration(false)
            channel.setSound(null, null)
            // Set the Notification Channel for the Notification Manager.
            mNotificationManager?.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val event = GeofencingEvent.fromIntent(intent)

        if (event == null || event.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorString(this, event.errorCode)
            LOGE(TAG, errorMessage)
            return START_REDELIVER_INTENT
        }

        val geofenceTransition = event.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT
        ) {

            val triggeringGeofences = event.triggeringGeofences

            val trigger = Trigger(
                type = GEOFENCE,
                value = triggeringGeofences[0].requestId,
                event = getTransitionString(geofenceTransition)
            )

            sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(this, trigger))
        } else {
            LOGE(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition))
        }

        return START_NOT_STICKY
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mChangingConfiguration = true
    }

    override fun onBind(intent: Intent): IBinder? {
        // Called when a client (MainActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        LOGI(TAG, "in onBind()")
        stopForeground(true)
        mChangingConfiguration = false
        return mBinder
    }

    inner class GeofenceBinder : Binder() {
        internal val service: GeofenceTransitionsService
            get() = this@GeofenceTransitionsService
    }

    override fun onRebind(intent: Intent) {
        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        LOGI(TAG, "in onRebind()")
        stopForeground(true)
        mChangingConfiguration = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        LOGI(TAG, "Last client unbound from service")

        // Called when the last client (MainActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration) {
            LOGI(TAG, "Starting foreground service")
            startForeground(NOTIFICATION_ID, getNotification())
        }
        return true // Ensures onRebind() is called when a client re-binds.
    }

    override fun onDestroy() {
        mServiceHandler?.removeCallbacksAndMessages(null)
    }

    private fun getTransitionString(transitionType: Int): String = when (transitionType) {
        Geofence.GEOFENCE_TRANSITION_ENTER -> getString(R.string.geofence_transition_entered)
        Geofence.GEOFENCE_TRANSITION_EXIT -> getString(R.string.geofence_transition_exited)
        Geofence.GEOFENCE_TRANSITION_DWELL -> getString(R.string.geofence_transition_stay)
        else -> getString(R.string.unknown_geofence_transition)
    }

    private fun getNotification(): Notification {
        LogUtils.LOGD(TAG, "showNotification()")
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL)
            .setSmallIcon(R.drawable.ox_notification_large_icon)
            .setContentTitle(getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(0, 0, 0))
            .setLights(Color.RED, 0, 100)
            .setSound(null)
            .build()
    }

    companion object {
        private val TAG = LogUtils.makeLogTag(GeofenceTransitionsService::class.java)
        private const val PRIMARY_CHANNEL = "default"
        private const val NOTIFICATION_ID = 0x789

        fun start(context: Context) {
            val intent = Intent(context, GeofenceTransitionsService::class.java)
            try {
                context.startService(intent)
            } catch (exception: IllegalStateException) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                }
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, GeofenceTransitionsService::class.java)
            try {
                context.stopService(intent)
            } catch (exception: Exception) {
                LOGE(TAG, "Stop GeofenceTransitionsService")
            }
        }
    }
}