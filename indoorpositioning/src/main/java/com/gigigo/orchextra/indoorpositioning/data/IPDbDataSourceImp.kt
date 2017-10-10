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

package com.gigigo.orchextra.indoorpositioning.data

import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.list.ListCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.ttl.TtlCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.persistors.Persistor
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.gigigo.orchextra.indoorpositioning.data.models.DbOxBeacon
import com.gigigo.orchextra.indoorpositioning.data.models.toDbOxBeacon
import com.gigigo.orchextra.indoorpositioning.data.models.toOxBeacon
import com.gigigo.orchextra.indoorpositioning.data.persistors.OxBeaconPersistor
import com.gigigo.orchextra.indoorpositioning.domain.datasource.IPDbDataSource
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.j256.ormlite.dao.Dao
import java.sql.SQLException
import java.util.concurrent.TimeUnit.DAYS

class IPDbDataSourceImp(helper: IPDatabaseHelper) : IPDbDataSource {

  private val daoOxBeacons: Dao<DbOxBeacon, Int> = helper.getOxBeaconDao()
  private val oxBeaconPersistor: Persistor<DbOxBeacon> = OxBeaconPersistor(helper)
  private val beaconListCachingStrategy = ListCachingStrategy(
      TtlCachingStrategy<DbOxBeacon>(30, DAYS))

  @Throws(DbException::class)
  override fun getBeacon(id: String): OxBeacon? {
    try {
      val dbOXBeacon = daoOxBeacons.queryBuilder().where().eq("value", id).queryForFirst()
      removeOldBeacons()

      return dbOXBeacon?.toOxBeacon()
    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  override fun getBeacons(): List<OxBeacon> {
    try {
      val dbOXBeacon = daoOxBeacons.queryForAll()

      return dbOXBeacon.map { it.toOxBeacon() }
    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  @Throws(DbException::class)
  override fun saveOrUpdateBeacon(beacon: OxBeacon) {
    try {
      val dbBeacon = beacon.toDbOxBeacon()
      dbBeacon.dbPersistedTime = System.currentTimeMillis()
      oxBeaconPersistor.persist(dbBeacon)

    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  @Throws(DbException::class)
  override fun removeBeacon(id: String) {
    try {
      val deleteBuilder = daoOxBeacons.deleteBuilder()
      deleteBuilder.where().`in`("value", id)
      deleteBuilder.delete()

    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  @Throws(SQLException::class)
  private fun removeOldBeacons() {
    val dbTriggers = daoOxBeacons.queryForAll()

    if (!beaconListCachingStrategy.isValid(dbTriggers)) {
      deleteDbBeacons(beaconListCachingStrategy.candidatesToPurgue(dbTriggers))
    }
  }

  @Throws(SQLException::class)
  private fun deleteDbBeacons(purgue: List<DbOxBeacon>) {
    internalDeleteBeacons(purgue.map { it.value })
  }

  @Throws(SQLException::class)
  private fun internalDeleteBeacons(beaconIds: List<String>) {
    val deleteBuilder = daoOxBeacons.deleteBuilder()
    deleteBuilder.where().`in`("value", beaconIds)
    deleteBuilder.delete()
  }
}