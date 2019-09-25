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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Notification
import com.gigigo.orchextra.core.utils.LogUtils

class NotificationActivity : AppCompatActivity() {

    private lateinit var actionHandlerServiceExecutor: ActionHandlerServiceExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create(this)
        title = ""

        showDialog()
    }

    private fun showDialog() {
        val notification = getNotification()
        val action = getAction()

        val builder = AlertDialog.Builder(this)
        val dialog = builder.setTitle(notification.title)
            .setMessage(notification.body)
            .setIcon(R.drawable.ox_notification_large_icon)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                actionHandlerServiceExecutor.execute(action.copy(notification = Notification()))
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()

        dialog.setOnDismissListener { finish() }
    }

    private fun getNotification(): Notification =
        intent.getParcelableExtra(NOTIFICATION_EXTRA) ?: Notification()

    private fun getAction(): Action = intent.getParcelableExtra(ACTION_EXTRA) ?: Action()

    companion object Navigator {

        private val TAG = LogUtils.makeLogTag(NotificationActivity::class.java)
        private const val NOTIFICATION_EXTRA = "notification_extra"
        private const val ACTION_EXTRA = "action_extra"

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
