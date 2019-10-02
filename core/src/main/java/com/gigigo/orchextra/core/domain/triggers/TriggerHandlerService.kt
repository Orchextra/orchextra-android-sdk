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

package com.gigigo.orchextra.core.domain.triggers

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import android.util.Log
import com.gigigo.orchextra.core.Orchextra.NOTIFICATION_CHANNEL
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.R.string
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.utils.LogUtils

class TriggerHandlerService : IntentService(TAG) {

    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL, getString(string.app_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                lightColor = Color.RED
                lockscreenVisibility = android.app.Notification.VISIBILITY_PRIVATE
                enableVibration(false)
                setSound(null, null)
            }
            manager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ox_notification_alpha_small_icon)
            .setContentTitle(getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVibrate(longArrayOf(0, 0, 0))
            .setLights(Color.RED, 0, 100)
            .setSound(null)

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()

        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
    }

    override fun onHandleIntent(intent: Intent?) {

        val triggerListener: TriggerListener = TriggerManager.create(this)
        val trigger = intent?.getParcelableExtra<Trigger>(TRIGGER_EXTRA)
        if (trigger == null) {
            Log.e(TAG, "trigger empty")
            return
        }
        triggerListener.onTriggerDetected(trigger)
    }

    companion object Navigator {
        private val TAG = LogUtils.makeLogTag(TriggerHandlerService::class.java)
        private const val TRIGGER_EXTRA = "trigger_extra"
        private const val NOTIFICATION_ID = 0x654

        fun start(context: Context, trigger: Trigger) {
            val intent = Intent(context, TriggerHandlerService::class.java)
            intent.putExtra(TRIGGER_EXTRA, trigger)
            try {
                context.startService(intent)
            } catch (exception: IllegalStateException) {
                if (VERSION.SDK_INT >= VERSION_CODES.O) {
                    context.startForegroundService(intent)
                }
            }
        }
    }
}