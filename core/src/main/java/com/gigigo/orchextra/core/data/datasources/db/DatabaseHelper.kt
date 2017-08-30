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
import com.gigigo.orchextra.core.data.datasources.db.models.DbTrigger
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class DatabaseHelper constructor(val context: Context) : OrmLiteSqliteOpenHelper(context,
    "OXDataBase", null, DATABASE_VERSION) {


  override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
    try {
      TableUtils.createTable<DbTrigger>(connectionSource, DbTrigger::class.java)
    } catch (e: SQLException) {
      Log.e(TAG, "Unable to create tables", e)
    }
  }

  override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?,
      oldVersion: Int, newVersion: Int) {
    try {
      TableUtils.dropTable<DbTrigger, Any>(connectionSource, DbTrigger::class.java, true)
    } catch (e: SQLException) {
      Log.e(TAG, "Unable to drop tables", e)
    }
  }

  fun getTriggerDao(): Dao<DbTrigger, Int> =
      getDao<Dao<DbTrigger, Int>, DbTrigger>(DbTrigger::class.java)

  companion object Factory {
    private val TAG = "DatabaseHelper"
    private val DATABASE_VERSION = 1

    fun create(context: Context): DatabaseHelper = DatabaseHelper(context)
  }
}