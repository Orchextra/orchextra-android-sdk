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
import android.content.Context
import android.content.SharedPreferences
import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.list.ListCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.ttl.TtlCachingStrategy
import com.gigigo.orchextra.core.data.datasources.db.models.DbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toDbTrigger
import com.gigigo.orchextra.core.data.datasources.db.models.toTrigger
import com.gigigo.orchextra.core.data.datasources.db.persistors.Persistor
import com.gigigo.orchextra.core.data.datasources.db.persistors.TriggerPersistor
import com.gigigo.orchextra.core.data.datasources.network.models.ApiOxCrm
import com.gigigo.orchextra.core.data.datasources.network.models.ApiOxDevice
import com.gigigo.orchextra.core.data.datasources.network.models.toApiOxCrm
import com.gigigo.orchextra.core.data.datasources.network.models.toApiOxDevice
import com.gigigo.orchextra.core.data.datasources.network.models.toOxCrm
import com.gigigo.orchextra.core.data.datasources.network.models.toOxDevice
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.EMPTY_CRM
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.gigigo.orchextra.core.utils.extensions.getBaseApiOxDevice
import com.j256.ormlite.dao.Dao
import com.squareup.moshi.Moshi
import java.sql.SQLException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.DAYS


class DbDataSourceImp(private val context: Context,
    private val sharedPreferences: SharedPreferences,
    helper: DatabaseHelper,
    moshi: Moshi) : DbDataSource {

  private val daoTriggers: Dao<DbTrigger, Int> = helper.getTriggerDao()
  private val triggerListCachingStrategy = ListCachingStrategy(
      TtlCachingStrategy<DbTrigger>(15, DAYS))
  private val triggerPersistor: Persistor<DbTrigger> = TriggerPersistor(helper)

  private val configurationAdapter = moshi.adapter(Configuration::class.java)
  private val crmJsonAdapter = moshi.adapter(ApiOxCrm::class.java)
  private val deviceJsonAdapter = moshi.adapter(ApiOxDevice::class.java)


  @SuppressLint("ApplySharedPref")
  override fun saveConfiguration(configuration: Configuration) {
    val editor = sharedPreferences.edit()
    editor?.putString(CONFIGURATION, configurationAdapter.toJson(configuration))
    editor?.commit()
  }

  override fun getConfiguration(): Configuration = try {
    val jsonData = sharedPreferences.getString(CONFIGURATION, null)
    configurationAdapter.fromJson(jsonData)
  } catch (e: Exception) {
    throw DbException(-1, e.message ?: "DbException")
  }

  override fun getTrigger(value: String): Trigger {
    try {
      val dbTrigger = daoTriggers.queryBuilder().where().eq("value", value).queryForFirst()
      removeOldTriggers()

      return dbTrigger?.toTrigger() ?: Trigger(VOID, "")
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

    return if (stringCrm.isNotEmpty()) {
      crmJsonAdapter.fromJson(stringCrm).toOxCrm()
    } else {
      EMPTY_CRM
    }
  }

  @SuppressLint("ApplySharedPref")
  override fun saveCrm(crm: OxCRM) {
    val editor = sharedPreferences.edit()
    editor?.putString(CRM_KEY, crmJsonAdapter.toJson(crm.toApiOxCrm()))
    editor?.commit()
  }

  @SuppressLint("ApplySharedPref")
  override fun clearCrm() {
    val editor = sharedPreferences.edit()
    editor?.putString(CRM_KEY, "")
    editor?.commit()
  }

  override fun getDevice(): OxDevice {
    val stringDevice = sharedPreferences.getString(DEVICE_KEY, "")

    return if (stringDevice.isNotEmpty()) {
      deviceJsonAdapter.fromJson(stringDevice).toOxDevice()
    } else {

      val anonymous = getAnonymous()
      var newDevice = context.getBaseApiOxDevice(anonymous).toOxDevice()

      val deviceBusinessUnits = getDeviceBusinessUnits()
      if (deviceBusinessUnits.isNotEmpty()) {
        newDevice = newDevice.copy(businessUnits = deviceBusinessUnits)
      }

      saveDevice(newDevice)
      newDevice
    }
  }

  @SuppressLint("ApplySharedPref")
  override fun saveDevice(device: OxDevice) {
    val editor = sharedPreferences.edit()
    editor?.putString(DEVICE_KEY, deviceJsonAdapter.toJson(device.toApiOxDevice()))
    editor?.commit()
  }

  @SuppressLint("ApplySharedPref")
  override fun clearDevice() {
    val editor = sharedPreferences.edit()
    editor?.putString(DEVICE_KEY, "")
    editor?.commit()
  }

  @SuppressLint("ApplySharedPref")
  override fun saveWaitTime(waitTime: Long) {
    val editor = sharedPreferences.edit()
    editor?.putLong(WAIT_TIME_KEY, waitTime)
    editor?.commit()
  }

  override fun getWaitTime(): Long =
      sharedPreferences.getLong(WAIT_TIME_KEY, TimeUnit.SECONDS.toMillis(120))

  @SuppressLint("ApplySharedPref")
  override fun saveScanTime(scanTimeInMillis: Long) {
    val editor = sharedPreferences.edit()
    editor?.putLong(SCAN_TIME_KEY, scanTimeInMillis)
    editor?.commit()
  }

  override fun getScanTime(): Long =
      sharedPreferences.getLong(SCAN_TIME_KEY, TimeUnit.SECONDS.toMillis(60))

  @SuppressLint("ApplySharedPref")
  override fun saveNotificationActivityName(notificationActivityName: String) {
    val editor = sharedPreferences.edit()
    editor?.putString(NOTIFICATION_ACTIVITY_KEY, notificationActivityName)
    editor?.commit()
  }

  override fun getNotificationActivityName(): String = sharedPreferences.getString(
      NOTIFICATION_ACTIVITY_KEY, "")

  override fun setAnonymous(anonymous: Boolean) {
    val editor = sharedPreferences.edit()
    editor?.putBoolean(ANONYMOUS_KEY, anonymous)
    editor?.commit()
  }

  override fun getAnonymous(): Boolean = sharedPreferences.getBoolean(ANONYMOUS_KEY, false)

  @SuppressLint("ApplySharedPref")
  override fun saveDeviceBusinessUnits(deviceBusinessUnits: List<String>) {
    val businessUnitsString = deviceBusinessUnits.reduce { acc, s -> acc + ";" + s }
    val editor = sharedPreferences.edit()
    editor?.putString(BUSINESS_UNITS, businessUnitsString)
    editor?.commit()
  }

  private fun getDeviceBusinessUnits(): List<String> {
    val businessUnitsString = sharedPreferences.getString(BUSINESS_UNITS, "")
    return businessUnitsString.split(";")
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

  companion object {
    private const val TAG = "DbDataSourceImp"
    private const val CRM_KEY = "crm_key"
    private const val DEVICE_KEY = "device_key"
    private const val WAIT_TIME_KEY = "wait_time_key"
    private const val SCAN_TIME_KEY = "wait_time_key"
    private const val NOTIFICATION_ACTIVITY_KEY = "notification_activity_key"
    private const val ANONYMOUS_KEY = "ANONYMOUS_KEY"
    private const val BUSINESS_UNITS = "business_units"
    private const val CONFIGURATION = "configuration"
  }
}