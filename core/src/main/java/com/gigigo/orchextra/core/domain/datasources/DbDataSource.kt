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

package com.gigigo.orchextra.core.domain.datasources

import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.data.datasources.db.DatabaseHelper
import com.gigigo.orchextra.core.data.datasources.db.DbDataSourceImp
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.exceptions.DbException

interface DbDataSource {

  @Throws(DbException::class)
  fun getTrigger(value: String): Trigger

  @Throws(DbException::class)
  fun saveTrigger(trigger: Trigger)

  companion object Factory {

    var dbDataSource: DbDataSource? = null

    fun create(): DbDataSource {

      if (dbDataSource == null) {
        dbDataSource = DbDataSourceImp(DatabaseHelper.create(Orchextra.provideContext()))
      }

      return dbDataSource as DbDataSource
    }
  }
}