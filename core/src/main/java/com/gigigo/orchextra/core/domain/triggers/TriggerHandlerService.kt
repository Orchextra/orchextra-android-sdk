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
import android.support.v4.app.NotificationCompat
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.R.string
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.utils.LogUtils

class TriggerHandlerService : IntentService(TAG) {

  override fun onCreate() {
    super.onCreate()
    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (VERSION.SDK_INT >= VERSION_CODES.O) {
      val chan1 = NotificationChannel(PRIMARY_CHANNEL, getString(string.app_name),
          NotificationManager.IMPORTANCE_DEFAULT)
      chan1.lightColor = Color.RED
      chan1.lockscreenVisibility = android.app.Notification.VISIBILITY_PRIVATE
      manager.createNotificationChannel(chan1)
    }

    val mBuilder = NotificationCompat.Builder(this, PRIMARY_CHANNEL)
        .setSmallIcon(R.drawable.ox_notification_large_icon)
        .setContentTitle(getString(R.string.app_name))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setVibrate(longArrayOf(0, 0, 0))
        .setLights(Color.RED, 0, 100)
        .setSound(null)

    startForeground(NOTIFICATION_ID, mBuilder.build())
  }

  override fun onDestroy() {
    super.onDestroy()

    val notificationManager = applicationContext.getSystemService(
        Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(NOTIFICATION_ID)
  }

  override fun onHandleIntent(intent: Intent) {

    val triggerListener: TriggerListener = TriggerManager.create(this)
    val trigger = intent.getParcelableExtra<Trigger>(TRIGGER_EXTRA)
    triggerListener.onTriggerDetected(trigger)
  }

  companion object Navigator {
    private val TAG = LogUtils.makeLogTag(TriggerHandlerService::class.java)
    private const val TRIGGER_EXTRA = "trigger_extra"
    private const val PRIMARY_CHANNEL = "default"
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