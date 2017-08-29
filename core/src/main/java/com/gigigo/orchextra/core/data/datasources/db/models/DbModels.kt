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


data class DbAction(
    @DatabaseField(generatedId = true, columnName = "id")
    var id: Int = 0,
    @DatabaseField(columnName = "trackId") val trackId: String,
    @DatabaseField val type: String,
    @DatabaseField val url: String,
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    val notification: DbNotification = DbNotification(),
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    val schedule: DbSchedule = DbSchedule(),
    @DatabaseField var dbPersistedTime: Long = 0) : TtlCachingObject {

  override fun getPersistedTime(): Long = dbPersistedTime
}

data class DbNotification(
    @DatabaseField(generatedId = true, columnName = "id")
    var id: Int = 0,
    @DatabaseField val title: String = "",
    @DatabaseField val body: String = "")

data class DbSchedule(
    @DatabaseField(generatedId = true, columnName = "id")
    var id: Int = 0,
    @DatabaseField val seconds: Int = -1,
    @DatabaseField val cancelable: Boolean = true)

data class DbTrigger constructor(
    @DatabaseField(generatedId = true, columnName = "id")
    var id: Int = 0,
    @DatabaseField val type: String,
    @DatabaseField(columnName = "value") val value: String,
    @DatabaseField val lat: Double? = null,
    @DatabaseField val lng: Double? = null,
    @DatabaseField val event: String? = null,
    @DatabaseField val phoneStatus: String? = null,
    @DatabaseField val distance: String? = null,
    @DatabaseField val temperature: Float? = null,
    @DatabaseField val battery: Long? = null,
    @DatabaseField val uptime: Long? = null,
    @DatabaseField var dbPersistedTime: Long = 0) : TtlCachingObject {

  override fun getPersistedTime(): Long = dbPersistedTime
}