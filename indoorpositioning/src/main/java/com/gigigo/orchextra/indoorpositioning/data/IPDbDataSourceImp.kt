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

import com.gigigo.orchextra.indoorpositioning.domain.datasource.IPDbDataSource
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon

class IPDbDataSourceImp : IPDbDataSource {

  override fun getBeacon(id: String): OxBeacon? {
    TODO("not implemented")
  }

  override fun saveOrUpdateBeacon(beacon: OxBeacon) {
    TODO("not implemented")
  }

  override fun removeBeacon(id: String) {
    TODO("not implemented")
  }
}