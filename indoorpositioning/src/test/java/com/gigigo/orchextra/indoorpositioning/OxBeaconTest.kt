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

import com.gigigo.orchextra.core.domain.entities.Proximity
import com.gigigo.orchextra.indoorpositioning.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.utils.extensions.isInRegion
import org.amshove.kluent.shouldBe
import org.junit.Test

class OxBeaconTest {

  @Test
  fun beaconShouldBeInRegion() {

    val config = getConfig()
    val testOxBeacon = OxBeacon(uuid = "uuid1",
        major = "2",
        minor = "1")

    val isInRegion = testOxBeacon.isInRegion(config)

    isInRegion.shouldBe(true)
  }

  @Test
  fun beaconShouldNotBeInRegion() {

    val config = getConfig()
    val testOxBeacon = OxBeacon(uuid = "uuid1",
        major = "6",
        minor = "1")

    val isInRegion = testOxBeacon.isInRegion(config)

    isInRegion.shouldBe(false)
  }


  fun getConfig(): List<Proximity> {

    val config: MutableList<Proximity> = arrayListOf()

    config.add(Proximity(
        code = "code1",
        uuid = "uuid1",
        minor = 1,
        major = 2,
        notifyOnEntry = true,
        notifyOnExit = true))

    config.add(Proximity(
        code = "code2",
        uuid = "uuid1",
        minor = 1,
        major = 3,
        notifyOnEntry = true,
        notifyOnExit = true))

    config.add(Proximity(
        code = "code3",
        uuid = "uuid1",
        minor = 2,
        major = 3,
        notifyOnEntry = true,
        notifyOnExit = true))

    config.add(Proximity(
        code = "code4",
        uuid = "uuid2",
        minor = 1,
        major = 3,
        notifyOnEntry = true,
        notifyOnExit = true))

    return config
  }
}