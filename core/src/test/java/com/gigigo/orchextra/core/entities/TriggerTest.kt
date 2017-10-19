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

package com.gigigo.orchextra.core.entities

import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON_REGION
import com.gigigo.orchextra.core.domain.entities.TriggerType.EDDYSTONE
import com.gigigo.orchextra.core.domain.entities.TriggerType.EDDYSTONE_REGION
import com.gigigo.orchextra.core.domain.entities.TriggerType.GEOFENCE
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class TriggerTest {

  @Test
  fun shouldBeAVoidTrigger() {
    val trigger = Trigger(VOID, "")

    val isVoid = trigger.isVoid()

    isVoid.shouldBeTrue()
  }

  @Test
  fun beaconShouldBeABackgroundTrigger() {
    val trigger = BEACON withValue "test"

    val isBackgroundTrigger = trigger.isBackgroundTrigger()

    isBackgroundTrigger.shouldBeTrue()
  }

  @Test
  fun beaconRegionShouldBeABackgroundTrigger() {
    val trigger = BEACON_REGION withValue "test"

    val isBackgroundTrigger = trigger.isBackgroundTrigger()

    isBackgroundTrigger.shouldBeTrue()
  }

  @Test
  fun eddystoneShouldBeABackgroundTrigger() {
    val trigger = EDDYSTONE withValue "test"

    val isBackgroundTrigger = trigger.isBackgroundTrigger()

    isBackgroundTrigger.shouldBeTrue()
  }

  @Test
  fun eddystoneRegionShouldBeABackgroundTrigger() {
    val trigger = EDDYSTONE_REGION withValue "test"

    val isBackgroundTrigger = trigger.isBackgroundTrigger()

    isBackgroundTrigger.shouldBeTrue()
  }

  @Test
  fun geofenceShouldBeABackgroundTrigger() {
    val trigger = GEOFENCE withValue "test"

    val isBackgroundTrigger = trigger.isBackgroundTrigger()

    isBackgroundTrigger.shouldBeTrue()
  }

  @Test
  fun qrShouldNotBeABackgroundTrigger() {
    val trigger = QR withValue "test"

    val isBackgroundTrigger = trigger.isBackgroundTrigger()

    isBackgroundTrigger.shouldBeFalse()
  }
}