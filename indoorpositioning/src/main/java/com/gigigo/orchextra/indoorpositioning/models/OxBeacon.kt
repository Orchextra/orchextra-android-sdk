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

package com.gigigo.orchextra.indoorpositioning.models

class OxBeacon {

  companion object {
    const val TYPE_EDDYSTONE_UID = 0
    const val TYPE_EDDYSTONE_URL = 1
    const val TYPE_ALTBEACON = 2
    const val TYPE_IBEACON = 3
  }

  var hashcode: Int = 0 // hashcode()
  var beaconType: Int = 0 // Eddystone, altBeacon, iBeacon
  var beaconAddress: String? = null // MAC address of the bluetooth emitter
  var uuid: String? = null
  var major: String? = null
  var minor: String? = null
  var txPower: Int = 0
  var rssi: Int = 0
  var distance: Double = 0.toDouble()
  var lastSeen: Long = 0
  var lastMinuteSeen: Long = 0
  var manufacturer: Int = 0
  var url: String? = null
  var namespaceId: String? = null
  var instanceId: String? = null
  var hasTelemetryData: Boolean = false
  var telemetryVersion: Long = 0
  var batteryMilliVolts: Long = 0
  private var temperature: Float = 0F

  var pduCount: Long = 0
  var uptime: Long = 0

  fun getTemperature(): Float {
    val tmp = temperature / 256F

    if (tmp == (1 shl 7).toFloat()) { // 0x8000
      return 0F
    }
    return if (tmp > (1 shl 7)) tmp - (1 shl 8) else tmp
  }

  fun setTemperature(temperature: Float) {
    this.temperature = temperature
  }
}