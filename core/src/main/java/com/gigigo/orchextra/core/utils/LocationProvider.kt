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

package com.gigigo.orchextra.core.utils

import android.content.Context
import android.location.Location
import com.gigigo.orchextra.core.domain.entities.Point
import com.google.android.gms.location.LocationServices

class LocationProvider(context: Context) {

  private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(
      context)

  fun getLocation(listener: (Point) -> Unit) {

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->

          val point = if (location != null) {
            Point(lat = location.latitude, lng = location.longitude)
          } else {
            Point(lat = 0.0, lng = 0.0)
          }

          listener(point)
        }
  }
}