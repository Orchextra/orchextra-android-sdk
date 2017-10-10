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

package com.gigigo.orchextra.indoorpositioning

import com.gigigo.orchextra.indoorpositioning.domain.datasource.IPDbDataSource
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getValue
import java.util.Date

class FakeIPDbDataSource : IPDbDataSource {

  private val beaconDb = mutableListOf<OxBeacon>()

  init {
    beaconDb.add(SAVED_BEACON)
    beaconDb.add(SAVED_OLD_BEACON)
  }

  override fun getBeacon(id: String): OxBeacon? {
    beaconDb.map { if (id == it.getValue()) return it }
    return null
  }

  override fun getBeacons(): List<OxBeacon> = beaconDb

  override fun saveOrUpdateBeacon(beacon: OxBeacon) {
    beaconDb.add(beacon)
  }

  override fun removeBeacon(id: String) {
    val beacon = getBeacon(id)
    if (beacon != null) {
      beaconDb.remove(beacon)
    }
  }

  companion object {
    val SAVED_BEACON = OxBeacon(
        uuid = "test_uuid",
        minor = 4,
        major = 9,
        lastDetection = Date())

    val SAVED_OLD_BEACON = OxBeacon(
        uuid = "test_uuid_old",
        minor = 5,
        major = 10,
        lastDetection = Date(50 * 1000))
  }
}