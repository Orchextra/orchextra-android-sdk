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

package com.gigigo.orchextra.indoorpositioning.domain

import com.gigigo.orchextra.indoorpositioning.domain.datasource.IPDbDataSource
import com.gigigo.orchextra.indoorpositioning.domain.models.ENTER_EVENT
import com.gigigo.orchextra.indoorpositioning.domain.models.EXIT_EVENT
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeaconRegion
import com.gigigo.orchextra.indoorpositioning.domain.models.STAY_EVENT
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getValue
import java.util.Date

class RegionsDetector(val ipdbDataSource: IPDbDataSource,
    val onRegionDetect: (beaconRegion: OxBeaconRegion) -> Unit) {

  val EXIT_TRIGGER_TIME_IN_SECONDS = 10

  fun onBeaconDetect(beacon: OxBeacon) {
    onRegionDetect(getRegionFromBeacon(beacon))
  }

  fun getRegionFromBeacon(beacon: OxBeacon, isFromTimer: Boolean = false): OxBeaconRegion {

    val savedBeacon = ipdbDataSource.getBeacon(beacon.getValue())

    return if (savedBeacon == null) {
      ipdbDataSource.saveOrUpdateBeacon(beacon)
      OxBeaconRegion(ENTER_EVENT, beacon)
    } else {
      if (isFromTimer && isExpired(savedBeacon)) {
        ipdbDataSource.removeBeacon(beacon.getValue())
        OxBeaconRegion(EXIT_EVENT, beacon)
      } else {
        ipdbDataSource.saveOrUpdateBeacon(beacon)
        OxBeaconRegion(STAY_EVENT, beacon)
      }
    }
  }

  private fun isExpired(beacon: OxBeacon): Boolean =
      beacon.lastDetection.time < Date().time - (EXIT_TRIGGER_TIME_IN_SECONDS * 1000)
}