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

package com.gigigo.orchextra.geofence

import com.gigigo.orchextra.core.domain.entities.GeoMarketing
import com.gigigo.orchextra.core.domain.entities.Point
import com.gigigo.orchextra.geofence.utils.toGeofence
import org.amshove.kluent.shouldEqual
import org.junit.Test

class MapperTest {

  @Test
  fun shouldMapGeoMarketingToGeofence() {

    val geoMarketing = GeoMarketing(code = "code",
        point = Point(1.0, 2.0),
        radius = 2,
        notifyOnEntry = true,
        notifyOnExit = true,
        stayTime = 10)

    val geofence = geoMarketing.toGeofence()

    geofence.requestId.shouldEqual("code")
  }
}