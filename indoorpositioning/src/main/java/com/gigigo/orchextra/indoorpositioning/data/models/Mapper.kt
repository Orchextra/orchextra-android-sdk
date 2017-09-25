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

package com.gigigo.orchextra.indoorpositioning.data.models

import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getValue
import java.util.Date


fun DbOxBeacon.toOxBeacon(): OxBeacon = with(this) {
  return OxBeacon(
      uuid = uuid,
      major = major,
      minor = minor,
      namespaceId = namespaceId,
      instanceId = instanceId,
      lastDetection = Date(lastDetection))
}

fun OxBeacon.toDbOxBeacon(): DbOxBeacon = with(this) {
  val dbOxBeacon = DbOxBeacon()
  dbOxBeacon.value = this.getValue()
  dbOxBeacon.uuid = this.uuid
  dbOxBeacon.major = this.major
  dbOxBeacon.minor = this.minor
  dbOxBeacon.namespaceId = this.namespaceId
  dbOxBeacon.instanceId = this.instanceId
  dbOxBeacon.lastDetection = lastDetection.time

  return dbOxBeacon
}