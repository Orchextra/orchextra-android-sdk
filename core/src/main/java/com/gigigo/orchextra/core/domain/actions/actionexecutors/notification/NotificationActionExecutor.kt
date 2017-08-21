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

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Handler
import android.os.Looper
import android.support.v4.app.NotificationCompat
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.domain.entities.Notification


class NotificationActionExecutor(private val context: Context) {

  private val handler = Handler(Looper.getMainLooper())

  fun showNotification(notification: Notification, actionExecutor: () -> Unit) {

    if (Orchextra.provideContext() != null) {
      val viewContext = Orchextra.provideContext() as Context
      handler.post { showDialog(viewContext, notification, actionExecutor) }
    } else {
      showBarNotification(notification, actionExecutor)
    }
  }

  private fun showDialog(context: Context, notification: Notification,
      actionExecutor: () -> Unit) = with(notification) {

    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
        .setMessage(body)
        .setIcon(R.drawable.ox_notification_large_icon)
        .setPositiveButton(android.R.string.ok, { dialog, _ ->
          dialog.dismiss()
          actionExecutor()
        })
        .show()
  }

  private fun showBarNotification(notification: Notification,
      actionExecutor: () -> Unit) = with(notification) {

    val notificationBuilder = NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ox_notification_large_icon)
        .setContentTitle(title)
        .setContentText(body)

    val mNotificationId = 1
    val mNotifyMgr = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    mNotifyMgr.notify(mNotificationId, notificationBuilder.build())
  }

  companion object Factory {

    fun create(context: Context): NotificationActionExecutor = NotificationActionExecutor(
        context)
  }
}