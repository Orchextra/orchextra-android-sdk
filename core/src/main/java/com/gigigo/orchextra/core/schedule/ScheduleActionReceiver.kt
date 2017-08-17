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

package com.gigigo.orchextra.core.schedule

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gigigo.orchextra.core.domain.actions.ActionDispatcher
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Schedule

class ScheduleActionReceiver : BroadcastReceiver() {

  private val actionDispatcher: ActionDispatcher = ActionDispatcher.create()

  override fun onReceive(context: Context, intent: Intent) {

    val action = intent.getParcelableExtra<Action>(ACTION_EXTRA)
    Log.d(TAG, "onScheduledActionReceive: $action")

    actionDispatcher.executeAction(action.copy(schedule = Schedule()))
  }

  companion object {
    private val TAG = "ScheduleActionReceiver"
    val ACTION_EXTRA = "action_extra"

    fun getSchedulerActionIntent(context: Context, action: Action): PendingIntent {

      val alarmIntent = Intent(context, ScheduleActionReceiver::class.java)
      alarmIntent.putExtra(ACTION_EXTRA, action)
      return PendingIntent.getBroadcast(context, 0, alarmIntent, 0)
    }
  }
}