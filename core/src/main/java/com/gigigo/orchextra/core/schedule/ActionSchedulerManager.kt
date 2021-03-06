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

import android.app.AlarmManager
import android.content.Context
import com.gigigo.orchextra.core.domain.entities.Action

class ActionSchedulerManager(val context: Context) {

  private val alarmManager: AlarmManager = context.getSystemService(
      Context.ALARM_SERVICE) as AlarmManager

  fun scheduleAction(action: Action) {
    val pendingIntent = ScheduleActionReceiver.getSchedulerActionIntent(context, action)

    alarmManager.set(AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis() + action.schedule.seconds * 1000, pendingIntent)
  }

  fun cancelScheduledAction(action: Action) {
    val pendingIntent = ScheduleActionReceiver.getSchedulerActionIntent(context, action)

    alarmManager.cancel(pendingIntent)
  }

  companion object Factory {

    fun create(context: Context): ActionSchedulerManager = ActionSchedulerManager(context)
  }
}