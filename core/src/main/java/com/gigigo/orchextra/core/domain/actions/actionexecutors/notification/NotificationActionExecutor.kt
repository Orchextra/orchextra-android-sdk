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

package com.gigigo.orchextra.core.domain.actions.actionexecutors.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import android.util.Log
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Notification

class NotificationActionExecutor(private val context: Context,
    private val dbDataSource: DbDataSource) {

  fun showNotification(notification: Notification, action: Action) {

    Log.d(TAG, "showNotification()")

    if (Orchextra.isActivityRunning()) {
      Log.d(TAG, "showDialog()")
      showDialog(context, notification, action)
    } else {
      showBarNotification(notification, action)
    }
  }

  private fun showDialog(context: Context, notification: Notification, action: Action) = with(
      notification) {

    try {
      NotificationActivity.open(context, notification, action)
    } catch (exception: Exception) {
      exception.printStackTrace()
    }
  }

  private fun showBarNotification(notification: Notification, action: Action) = with(notification) {

    val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    if (VERSION.SDK_INT >= VERSION_CODES.O) {
      val chan1 = NotificationChannel(PRIMARY_CHANNEL, context.getString(R.string.app_name),
          NotificationManager.IMPORTANCE_DEFAULT)
      chan1.lightColor = Color.RED
      chan1.lockscreenVisibility = android.app.Notification.VISIBILITY_PRIVATE
      manager.createNotificationChannel(chan1)
    }

    val notificationBuilder = NotificationCompat.Builder(context, PRIMARY_CHANNEL)
        .setSmallIcon(R.drawable.ox_notification_alpha_small_icon)
        .setContentTitle(title)
        .setContentText(body)

    val notificationIntent = NotificationActivity.getIntent(context, notification, action)
    val notificationActivityName = dbDataSource.getNotificationActivityName()

    val pendingIntent = if (notificationActivityName.isNotEmpty()) {
      val cls = Class.forName(notificationActivityName)
      val backIntent = Intent(context, cls)
      backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

      PendingIntent.getActivities(context, 0x73,
          arrayOf(backIntent, notificationIntent), PendingIntent.FLAG_ONE_SHOT)
    } else {
      PendingIntent.getActivity(context, 0x73, notificationIntent, PendingIntent.FLAG_ONE_SHOT)
    }

    notificationBuilder.setContentIntent(pendingIntent)
    notificationBuilder.setAutoCancel(true)

    val mNotificationId = 1

    manager.notify(mNotificationId, notificationBuilder.build())
  }

  companion object Factory {

    private const val TAG = "NotificationAction"
    private const val PRIMARY_CHANNEL = "default"

    fun create(context: Context): NotificationActionExecutor = NotificationActionExecutor(
        context, DbDataSource.create(context))
  }
}