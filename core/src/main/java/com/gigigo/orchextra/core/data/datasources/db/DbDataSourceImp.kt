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

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.list.ListCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.ttl.TtlCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.models.DbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toDbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toTrigger
import com.gigigo.orchextra.core.data.datasources.db.persistors.Persistor
import com.gigigo.orchextra.core.data.datasources.db.persistors.TriggerPersistor
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.j256.ormlite.dao.Dao
import com.squareup.moshi.Moshi
import java.sql.SQLException
import java.util.concurrent.TimeUnit.DAYS


class DbDataSourceImp(private val sharedPreferences: SharedPreferences, helper: DatabaseHelper,
    moshi: Moshi) : DbDataSource {

  private val CRM_KEY = "crm_key"
  private val DEVICE_KEY = "device_key"
  private val daoTriggers: Dao<DbTrigger, Int> = helper.getTriggerDao()
  private val triggerListCachingStrategy = ListCachingStrategy(
      TtlCachingStrategy<DbTrigger>(15, DAYS))
  private val triggerPersistor: Persistor<DbTrigger> = TriggerPersistor(helper)
  private val crmJsonAdapter = moshi.adapter(OxCRM::class.java)
  private val deviceJsonAdapter = moshi.adapter(OxDevice::class.java)


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

  override fun getCrm(): OxCRM {

    val stringCrm = sharedPreferences.getString(CRM_KEY, "")

    if (stringCrm.isNotEmpty()) {
      return crmJsonAdapter.fromJson(stringCrm)
    } else {
      throw DbException(-1, "crm null")
    }
  }

  @SuppressLint("CommitPrefEdits")
  override fun saveCrm(crm: OxCRM) {
    val editor = sharedPreferences.edit()
    editor?.putString(CRM_KEY, crmJsonAdapter.toJson(crm))
    editor?.commit()
  }

  override fun getDevice(): OxDevice {
    val stringDevice = sharedPreferences.getString(DEVICE_KEY, "")

    if (stringDevice.isNotEmpty()) {
      return deviceJsonAdapter.fromJson(stringDevice)
    } else {
      throw DbException(-1, "device null")
    }
  }

  @SuppressLint("CommitPrefEdits")
  override fun saveDevice(device: OxDevice) {
    val editor = sharedPreferences.edit()
    editor?.putString(DEVICE_KEY, deviceJsonAdapter.toJson(device))
    editor?.commit()
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