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
import com.gigigo.orchextra.core.data.datasources.db.models.DbAction
import com.gigigo.orchextra.core.data.datasources.db.models.DbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toAction
import com.gigigo.orchextra.core.data.datasources.db.models.toDbAction
import com.gigigo.orchextra.core.data.datasources.db.models.toDbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toTrigger
import com.gigigo.orchextra.core.data.datasources.db.persistors.ActionPersistor
import com.gigigo.orchextra.core.data.datasources.db.persistors.Persistor
import com.gigigo.orchextra.core.data.datasources.db.persistors.TriggerPersistor
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.j256.ormlite.dao.Dao
import java.sql.SQLException
import java.util.concurrent.TimeUnit.DAYS


class DbDataSourceImp(helper: DatabaseHelper) : DbDataSource {

  private val daoActions: Dao<DbAction, Int> = helper.getActionDao()!!
  private val daoTriggers: Dao<DbTrigger, Int> = helper.getTriggerDao()!!
  private val listCachingStrategy = ListCachingStrategy(TtlCachingStrategy<DbAction>(15, DAYS))
  private val triggerListCachingStrategy = ListCachingStrategy(
      TtlCachingStrategy<DbTrigger>(15, DAYS))
  private val persistor: Persistor<DbAction> = ActionPersistor(helper)
  private val triggerPersistor: Persistor<DbTrigger> = TriggerPersistor(helper)

  override fun getActions(): List<Action> {
    try {
      val dbActions = daoActions.queryForAll()

      if (!listCachingStrategy.isValid(dbActions)) {
        deleteDbActions(listCachingStrategy.candidatesToPurgue(dbActions))
      }

      return dbActions.map { it.toAction() }
    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  override fun saveActions(actions: List<Action>) {
    try {
      for (action in actions) {
        val dbAction = action.toDbAction()
        dbAction.dbPersistedTime = System.currentTimeMillis()
        persistor.persist(dbAction)
      }
    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  override fun removeActions(actions: List<Action>) {
    try {
      internalDeleteActions(actions.map { it.trackId })
    } catch (e: Throwable) {
      throw DbException(-1, e.message ?: "")
    }
  }

  override fun getTriggers(): List<Trigger> {
    try {
      val dbTriggers = daoTriggers.queryForAll()

      if (!triggerListCachingStrategy.isValid(dbTriggers)) {
        deleteDbTriggers(triggerListCachingStrategy.candidatesToPurgue(dbTriggers))
      }

      return dbTriggers.map { it.toTrigger() }
    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  override fun saveTriggers(triggers: List<Trigger>) {
    try {
      for (trigger in triggers) {
        val dbTrigger = trigger.toDbTrigger()
        dbTrigger.dbPersistedTime = System.currentTimeMillis()
        triggerPersistor.persist(dbTrigger)
      }
    } catch (e: SQLException) {
      throw DbException(-1, e.message ?: "")
    }
  }

  @Throws(SQLException::class)
  private fun deleteDbActions(purgue: List<DbAction>) {
    internalDeleteActions(purgue.map { it.trackId })
  }

  @Throws(SQLException::class)
  private fun internalDeleteActions(trackIds: List<String>) {
    val deleteBuilder = daoActions.deleteBuilder()
    deleteBuilder.where().`in`("trackId", trackIds)
    deleteBuilder.delete()
  }

  @Throws(SQLException::class)
  private fun deleteDbTriggers(purgue: List<DbTrigger>) {
    internalDeleteTriggers(purgue.map { it.value })
  }

  @Throws(SQLException::class)
  private fun internalDeleteTriggers(triggerIds: List<String>) {
    val deleteBuilder = daoActions.deleteBuilder()
    deleteBuilder.where().`in`("value", triggerIds)
    deleteBuilder.delete()
  }
}