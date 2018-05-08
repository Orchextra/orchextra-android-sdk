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

package com.gigigo.orchextra.indoorpositioning.domain.datasource

import android.content.Context
import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.gigigo.orchextra.indoorpositioning.data.IPDatabaseHelper
import com.gigigo.orchextra.indoorpositioning.data.IPDbDataSourceImp
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon

interface IPDbDataSource {

  @Throws(DbException::class)
  fun getBeacon(id: String): OxBeacon?

  @Throws(DbException::class)
  fun getBeacons(): List<OxBeacon>

  @Throws(DbException::class)
  fun saveOrUpdateBeacon(beacon: OxBeacon)

  @Throws(DbException::class)
  fun removeBeacon(id: String)

  @Throws(DbException::class)
  fun saveConfig(config: List<IndoorPositionConfig>)

  @Throws(DbException::class)
  fun getConfig(): List<IndoorPositionConfig>

  companion object Factory {

    var ipdbDataSource: IPDbDataSource? = null

    fun create(context: Context): IPDbDataSource {

      if (ipdbDataSource == null) {
        val sharedPref = context.getSharedPreferences("indoorpositioning", Context.MODE_PRIVATE)
        ipdbDataSource = IPDbDataSourceImp(IPDatabaseHelper.create(context), sharedPref)
      }
      return ipdbDataSource as IPDbDataSource
    }
  }
}