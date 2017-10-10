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

import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.indoorpositioning.domain.IndoorPositioningValidator
import com.gigigo.orchextra.indoorpositioning.domain.models.ENTER_EVENT
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeaconRegion
import com.gigigo.orchextra.indoorpositioning.utils.extensions.isInRegion
import org.amshove.kluent.shouldBe
import org.junit.Test
import java.util.Date

class IndoorPositioningValidatorTest {

  private val ENTRY_BEACON = OxBeacon(uuid = "entry",
      major = 1,
      minor = 1,
      lastDetection = Date())

  private val EXIT_BEACON = OxBeacon(uuid = "exit",
      major = 2,
      minor = 2,
      lastDetection = Date())

  private val UNKNOW_BEACON = OxBeacon(uuid = "unknow",
      major = 2,
      minor = 2,
      lastDetection = Date())

  @Test
  fun beaconShouldBeValidOnEntry() {

    val validator = getIndoorPositioningValidator()

    val testRegion = OxBeaconRegion(
        value = "value1",
        event = ENTER_EVENT,
        beacon = ENTRY_BEACON)

    val isValid = validator.isValid(testRegion)

    isValid.shouldBe(true)
  }

  @Test
  fun beaconShouldBeNotValidOnEntry() {

    val validator = getIndoorPositioningValidator()

    val testRegion = OxBeaconRegion(
        value = "value1",
        event = ENTER_EVENT,
        beacon = EXIT_BEACON)

    val isValid = validator.isValid(testRegion)

    isValid.shouldBe(false)
  }

  @Test
  fun beaconShouldNotBeInRegion() {

    val validator = getIndoorPositioningValidator()

    val testRegion = OxBeaconRegion(
        value = "value1",
        event = ENTER_EVENT,
        beacon = UNKNOW_BEACON)

    val isValid = validator.isValid(testRegion)

    isValid.shouldBe(false)
  }

  @Test
  fun testBeacons() {

    val config = getConfig()

    ENTRY_BEACON.isInRegion(config).shouldBe(true)
    EXIT_BEACON.isInRegion(config).shouldBe(true)
    UNKNOW_BEACON.isInRegion(config).shouldBe(false)
  }

  private fun getIndoorPositioningValidator(): IndoorPositioningValidator {
    val config = getConfig()
    return IndoorPositioningValidator(config)
  }

  private fun getConfig(): List<IndoorPositionConfig> {

    val entryBeaconConfig = IndoorPositionConfig(
        uuid = "entry",
        major = 1,
        minor = -1,
        notifyOnEntry = true,
        notifyOnExit = false)

    val exitBeaconConfig = IndoorPositionConfig(
        uuid = "exit",
        major = 2,
        minor = -1,
        notifyOnEntry = false,
        notifyOnExit = true)

    return listOf(entryBeaconConfig, exitBeaconConfig)
  }
}