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
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.utils.extensions.isInRegion
import org.amshove.kluent.shouldBe
import org.junit.Test
import java.util.Date

class OxBeaconTest {

  @Test
  fun beaconShouldBeInRegion() {

    val config = getConfig()
    val testOxBeacon = OxBeacon(uuid = "uuid1",
        major = "2",
        minor = "1",
        lastDetection = Date())

    val isInRegion = testOxBeacon.isInRegion(config)

    isInRegion.shouldBe(true)
  }

  @Test
  fun eddystoneShouldBeInRegion() {

    val config = getConfig()
    val testOxEddystone = OxBeacon(namespaceId = "namespace1",
        lastDetection = Date())

    val isInRegion = testOxEddystone.isInRegion(config)

    isInRegion.shouldBe(true)
  }

  @Test
  fun beaconShouldNotBeInRegion() {

    val config = getConfig()
    val testOxBeacon = OxBeacon(uuid = "uuid1",
        major = "6",
        minor = "1",
        lastDetection = Date())

    val isInRegion = testOxBeacon.isInRegion(config)

    isInRegion.shouldBe(false)
  }

  @Test
  fun eddystoneShouldNotBeInRegion() {

    val config = getConfig()
    val testOxEddystone = OxBeacon(namespaceId = "namespace2",
        lastDetection = Date())

    val isInRegion = testOxEddystone.isInRegion(config)

    isInRegion.shouldBe(false)
  }


  fun getConfig(): List<IndoorPositionConfig> {

    val config: MutableList<IndoorPositionConfig> = arrayListOf()

    config.add(IndoorPositionConfig(
        code = "code1",
        uuid = "uuid1",
        minor = 1,
        major = 2,
        notifyOnEntry = true,
        notifyOnExit = true))

    config.add(IndoorPositionConfig(
        code = "code2",
        uuid = "uuid1",
        minor = 1,
        major = 3,
        notifyOnEntry = true,
        notifyOnExit = true))

    config.add(IndoorPositionConfig(
        code = "code3",
        uuid = "uuid1",
        minor = 2,
        major = 3,
        notifyOnEntry = true,
        notifyOnExit = true))

    config.add(IndoorPositionConfig(
        code = "code4",
        uuid = "uuid2",
        minor = 1,
        major = 3,
        notifyOnEntry = true,
        notifyOnExit = true))

    config.add(IndoorPositionConfig(
        code = "code4",
        namespace = "namespace1",
        notifyOnEntry = true,
        notifyOnExit = true))

    return config
  }
}