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

import com.gigigo.orchextra.indoorpositioning.domain.RegionsDetector
import com.gigigo.orchextra.indoorpositioning.domain.models.ENTER_EVENT
import com.gigigo.orchextra.indoorpositioning.domain.models.EXIT_EVENT
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.domain.models.STAY_EVENT
import org.amshove.kluent.shouldEqualTo
import org.junit.Test
import java.util.Date

class RegionsDetectorTest {

  @Test
  fun shouldGetARegionWithEnterEvent() {
    val regionsDetector = getRegionsDetector()
    val beacon = OxBeacon(lastDetection = Date())

    val region = regionsDetector.getRegionFromBeacon(beacon)

    region.event.shouldEqualTo(ENTER_EVENT)
  }

  @Test
  fun shouldGetARegionWithStayEvent() {
    val regionsDetector = getRegionsDetector()
    val beacon = FakeIPDbDataSource.SAVED_BEACON

    val region = regionsDetector.getRegionFromBeacon(beacon)

    region.event.shouldEqualTo(STAY_EVENT)
  }

  @Test
  fun shouldGetARegionWithStayEventCalledFromTimer() {
    val regionsDetector = getRegionsDetector()
    val beacon = FakeIPDbDataSource.SAVED_BEACON

    val region = regionsDetector.getRegionFromBeacon(beacon, true)

    region.event.shouldEqualTo(STAY_EVENT)
  }

  @Test
  fun shouldGetARegionWithExitEvent() {
    val regionsDetector = getRegionsDetector()
    val beacon = FakeIPDbDataSource.SAVED_OLD_BEACON

    val region = regionsDetector.getRegionFromBeacon(beacon, true)

    region.event.shouldEqualTo(EXIT_EVENT)
  }

  private fun getRegionsDetector(): RegionsDetector = RegionsDetector(FakeIPDbDataSource(), {})
}