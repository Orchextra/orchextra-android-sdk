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
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Notification

class NotificationActivity : AppCompatActivity() {

  private val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification)
    title = ""

    val notification = getNotification()
    val action = getAction()

    val builder = AlertDialog.Builder(this)
    val dialog = builder.setTitle(notification.title)
        .setMessage(notification.body)
        .setIcon(R.drawable.ox_notification_large_icon)
        .setPositiveButton(android.R.string.ok, { dialog, _ ->
          dialog.dismiss()
          actionHandlerServiceExecutor.execute(this@NotificationActivity,
              action.copy(notification = Notification()))
        })
        .show()

    dialog.setOnDismissListener { finish() }
  }

  private fun getNotification(): Notification = intent.getParcelableExtra(NOTIFICATION_EXTRA)

  private fun getAction(): Action = intent.getParcelableExtra(ACTION_EXTRA)

  companion object Navigator {

    private val NOTIFICATION_EXTRA = "notification_extra"
    private val ACTION_EXTRA = "action_extra"

    fun open(context: Context, notification: Notification, action: Action) {
      val intent = getIntent(context, notification, action)
      context.startActivity(intent)
    }

    fun getIntent(context: Context, notification: Notification, action: Action): Intent {
      val intent = Intent(context, NotificationActivity::class.java)
      intent.putExtra(NOTIFICATION_EXTRA, notification)
      intent.putExtra(ACTION_EXTRA, action)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

      return intent
    }
  }
}
