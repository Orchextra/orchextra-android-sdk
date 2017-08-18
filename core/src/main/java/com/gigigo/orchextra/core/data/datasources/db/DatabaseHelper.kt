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

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.gigigo.orchextra.core.data.datasources.db.models.DbAction
import com.gigigo.orchextra.core.data.datasources.db.models.DbNotification
import com.gigigo.orchextra.core.data.datasources.db.models.DbSchedule
import com.gigigo.orchextra.core.data.datasources.db.models.DbTrigger
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils


class DatabaseHelper constructor(val context: Context) : OrmLiteSqliteOpenHelper(context,
    "OXDataBase", null,
    DATABASE_VERSION) {


  override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
    try {
      TableUtils.createTable<DbAction>(connectionSource, DbAction::class.java)
      TableUtils.createTable<DbNotification>(connectionSource, DbNotification::class.java)
      TableUtils.createTable<DbSchedule>(connectionSource, DbSchedule::class.java)
    } catch (e: SQLException) {
      Log.e(TAG, "Unable to create tables", e)
    }
  }

  override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?,
      oldVersion: Int, newVersion: Int) {
    try {
      TableUtils.dropTable<DbAction, Any>(connectionSource, DbAction::class.java, true)
      TableUtils.dropTable<DbNotification, Any>(connectionSource, DbNotification::class.java, true)
      TableUtils.dropTable<DbSchedule, Any>(connectionSource, DbSchedule::class.java, true)
    } catch (e: SQLException) {
      Log.e(TAG, "Unable to drop tables", e)
    }
  }

  fun getActionDao(): Dao<DbAction, Int>? {
    try {
      return getDao<Dao<DbAction, Int>, DbAction>(DbAction::class.java)
    } catch (e: SQLException) {
      e.printStackTrace()
    }

    return null
  }

  fun getNotificationDao(): Dao<DbNotification, Int>? {
    try {
      return getDao<Dao<DbNotification, Int>, DbNotification>(DbNotification::class.java)
    } catch (e: SQLException) {
      e.printStackTrace()
    }

    return null
  }

  fun getScheduleDao(): Dao<DbSchedule, Int>? {
    try {
      return getDao<Dao<DbSchedule, Int>, DbSchedule>(DbSchedule::class.java)
    } catch (e: SQLException) {
      e.printStackTrace()
    }

    return null
  }

  fun getTriggerDao(): Dao<DbTrigger, Int>? {
    try {
      return getDao<Dao<DbTrigger, Int>, DbTrigger>(DbTrigger::class.java)
    } catch (e: SQLException) {
      e.printStackTrace()
    }

    return null
  }

  companion object {
    private val TAG = "DatabaseHelper"
    private val DATABASE_VERSION = 1
  }
}