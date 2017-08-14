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

package com.gigigo.orchextra.geofence.utils

import com.gigigo.orchextra.core.domain.entities.GeoMarketing
import com.google.android.gms.location.Geofence


fun GeoMarketing.toGeofence(): Geofence = with(this) {

  return Geofence.Builder()
      .setRequestId(code)
      .setCircularRegion(point.lat, point.lng, radius.toFloat())
      .setExpirationDuration(stayTime.toLong() * 1000)
      .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
      .build()
}