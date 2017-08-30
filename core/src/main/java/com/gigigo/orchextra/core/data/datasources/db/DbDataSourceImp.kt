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

package com.gigigo.orchextra.core.data.datasources.db

import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.list.ListCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.ttl.TtlCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.models.DbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toDbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toTrigger
import com.gigigo.orchextra.core.data.datasources.db.persistors.Persistor
import com.gigigo.orchextra.core.data.datasources.db.persistors.TriggerPersistor
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.j256.ormlite.dao.Dao
import java.sql.SQLException
import java.util.concurrent.TimeUnit.DAYS

class DbDataSourceImp(helper: DatabaseHelper) : DbDataSource {

  private val daoTriggers: Dao<DbTrigger, Int> = helper.getTriggerDao()
  private val triggerListCachingStrategy = ListCachingStrategy(
      TtlCachingStrategy<DbTrigger>(15, DAYS))
  private val triggerPersistor: Persistor<DbTrigger> = TriggerPersistor(helper)

  override fun getTrigger(value: String): Trigger {
    try {
      val dbTrigger = daoTriggers.queryBuilder().where().eq("value", value).queryForFirst()
      removeOldTriggers()

      return if (dbTrigger == null) {
        Trigger(VOID, "")
      } else {
        dbTrigger.toTrigger()
      }
    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  override fun saveTrigger(trigger: Trigger) {
    try {
      val dbTrigger = trigger.toDbTrigger()
      dbTrigger.dbPersistedTime = System.currentTimeMillis()
      triggerPersistor.persist(dbTrigger)

    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  @Throws(SQLException::class)
  private fun removeOldTriggers() {
    val dbTriggers = daoTriggers.queryForAll()

    if (!triggerListCachingStrategy.isValid(dbTriggers)) {
      deleteDbTriggers(triggerListCachingStrategy.candidatesToPurgue(dbTriggers))
    }
  }

  @Throws(SQLException::class)
  private fun deleteDbTriggers(purgue: List<DbTrigger>) {
    internalDeleteTriggers(purgue.map { it.value })
  }

  @Throws(SQLException::class)
  private fun internalDeleteTriggers(triggerIds: List<String>) {
    val deleteBuilder = daoTriggers.deleteBuilder()
    deleteBuilder.where().`in`("value", triggerIds)
    deleteBuilder.delete()
  }
}