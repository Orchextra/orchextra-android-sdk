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

import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType
import com.gigigo.orchextra.core.domain.exceptions.DbException

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
      dbTrigger.detectedTime = detectedTime

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
          uptime = uptime,
          detectedTime = detectedTime)
    }

fun DbException.toError(): Error =
    with(this) {
      return Error(
          code = code,
          message = error)
    }