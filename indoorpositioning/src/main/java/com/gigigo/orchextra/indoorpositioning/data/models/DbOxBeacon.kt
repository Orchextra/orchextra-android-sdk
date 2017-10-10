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

import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.ttl.TtlCachingObject
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "trigger")
class DbOxBeacon : TtlCachingObject {

  @DatabaseField(generatedId = true, columnName = "id")
  var id: Int = 0

  @DatabaseField(columnName = "value")
  var value: String = ""

  @DatabaseField
  var type: Int = -1

  @DatabaseField
  var uuid: String = ""

  @DatabaseField
  var major: Int = -1

  @DatabaseField
  var minor: Int = -1

  @DatabaseField
  var namespaceId: String = ""

  @DatabaseField
  var instanceId: String = ""

  @DatabaseField
  var lastDetection: Long = 0

  @DatabaseField
  var dbPersistedTime: Long = 0

  override fun getPersistedTime(): Long = dbPersistedTime
}