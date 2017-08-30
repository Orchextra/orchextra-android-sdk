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

import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.ttl.TtlCachingObject
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable


@DatabaseTable(tableName = "action")
class DbAction : TtlCachingObject {

  @DatabaseField(generatedId = true, columnName = "id")
  var id: Int = 0

  @DatabaseField(columnName = "trackId")
  var trackId: String = ""

  @DatabaseField
  var type: String = ""

  @DatabaseField
  var url: String = ""

  @DatabaseField(foreign = true, foreignAutoRefresh = true)
  var notification: DbNotification = DbNotification()

  @DatabaseField(foreign = true, foreignAutoRefresh = true)
  var schedule: DbSchedule = DbSchedule()

  @DatabaseField
  var dbPersistedTime: Long = 0

  override fun getPersistedTime(): Long = dbPersistedTime
}

@DatabaseTable(tableName = "notification")
class DbNotification {

  @DatabaseField(generatedId = true, columnName = "id")
  var id: Int = 0

  @DatabaseField
  var title: String = ""

  @DatabaseField
  var body: String = ""
}


@DatabaseTable(tableName = "schedule")
class DbSchedule {

  @DatabaseField(generatedId = true, columnName = "id")
  var id: Int = 0

  @DatabaseField
  var seconds: Int = -1

  @DatabaseField
  var cancelable: Boolean = true
}


@DatabaseTable(tableName = "trigger")
class DbTrigger : TtlCachingObject {

  @DatabaseField(generatedId = true, columnName = "id")
  var id: Int = 0

  @DatabaseField
  var type: String = ""

  @DatabaseField(columnName = "value")
  var value: String = ""

  @DatabaseField
  var lat: Double? = null

  @DatabaseField
  var lng: Double? = null

  @DatabaseField
  var event: String? = null

  @DatabaseField
  var phoneStatus: String? = null

  @DatabaseField
  var distance: String? = null

  @DatabaseField
  var temperature: Float? = null

  @DatabaseField
  var battery: Long? = null

  @DatabaseField
  var uptime: Long? = null

  @DatabaseField
  var dbPersistedTime: Long = 0

  override fun getPersistedTime(): Long = dbPersistedTime
}