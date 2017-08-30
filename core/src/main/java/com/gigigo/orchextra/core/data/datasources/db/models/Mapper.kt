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

package com.gigigo.orchextra.core.data.datasources.db.models

import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType
import com.gigigo.orchextra.core.domain.entities.Notification
import com.gigigo.orchextra.core.domain.entities.Schedule
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType

fun DbAction.toAction(): Action =
    with(this) {
      return Action(
          trackId = trackId,
          type = ActionType.valueOf(type),
          url = url,
          notification = notification.toNotification(),
          schedule = schedule.toSchedule())
    }

fun DbNotification.toNotification(): Notification =
    with(this) {
      return Notification(
          title = title,
          body = body)
    }

fun DbSchedule.toSchedule(): Schedule =
    with(this) {
      return Schedule(
          seconds = seconds,
          cancelable = cancelable)
    }

fun Action.toDbAction(): DbAction =
    with(this) {

      val dbAction = DbAction()
      dbAction.trackId = trackId
      dbAction.type = type.name
      dbAction.url = url
      dbAction.notification = notification.toDbNotification()
      dbAction.schedule = schedule.toDbSchedule()

      return dbAction
    }

fun Notification.toDbNotification(): DbNotification =
    with(this) {

      val dbNotification = DbNotification()
      dbNotification.title = title
      dbNotification.body = body

      return dbNotification
    }

fun Schedule.toDbSchedule(): DbSchedule =
    with(this) {

      val dbSchedule = DbSchedule()
      dbSchedule.seconds = seconds
      dbSchedule.cancelable = cancelable

      return dbSchedule
    }

fun Trigger.toDbTrigger(): DbTrigger =
    with(this) {

      val dbTrigger = DbTrigger()
      dbTrigger.type = type.name
      dbTrigger.value = value
      dbTrigger.lat = lat
      dbTrigger.lng = lng
      dbTrigger.event = event
      dbTrigger.phoneStatus = phoneStatus
      dbTrigger.distance = distance
      dbTrigger.temperature = temperature
      dbTrigger.battery = battery
      dbTrigger.uptime = uptime

      return dbTrigger
    }

fun DbTrigger.toTrigger(): Trigger =
    with(this) {
      return Trigger(
          type = TriggerType.valueOf(type),
          value = value,
          lat = lat,
          lng = lng,
          event = event,
          phoneStatus = phoneStatus,
          distance = distance,
          temperature = temperature,
          battery = battery,
          uptime = uptime)
    }