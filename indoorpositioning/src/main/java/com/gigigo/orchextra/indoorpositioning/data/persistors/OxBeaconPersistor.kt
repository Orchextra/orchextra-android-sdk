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

package com.gigigo.orchextra.indoorpositioning.data.persistors

import com.gigigo.orchextra.core.data.datasources.db.persistors.Persistor
import com.gigigo.orchextra.indoorpositioning.data.IPDatabaseHelper
import com.gigigo.orchextra.indoorpositioning.data.models.DbOxBeacon
import java.sql.SQLException

class OxBeaconPersistor(private val helper: IPDatabaseHelper) : Persistor<DbOxBeacon> {

  @Throws(SQLException::class)
  override fun persist(data: DbOxBeacon) {

    val dbTrigger = helper.getOxBeaconDao().queryBuilder().where().eq("value",
        data.value).queryForFirst()

    if (dbTrigger == null) {
      helper.getOxBeaconDao().create(data)
    } else {
      data.id = dbTrigger.id
      helper.getOxBeaconDao().update(data)
    }
  }
}