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

package com.gigigo.orchextra.core.data.datasources.db.persistors

import com.gigigo.orchextra.core.data.datasources.db.DatabaseHelper
import com.gigigo.orchextra.core.data.datasources.db.models.DbTrigger
import java.sql.SQLException

class TriggerPersistor(private val helper: DatabaseHelper) : Persistor<DbTrigger> {

  @Throws(SQLException::class)
  override fun persist(data: DbTrigger) {

    helper.getTriggerDao()?.create(data)
  }
}