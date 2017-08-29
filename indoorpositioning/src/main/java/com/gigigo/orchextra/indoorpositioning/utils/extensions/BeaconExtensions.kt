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

package com.gigigo.orchextra.indoorpositioning.utils.extensions

import com.gigigo.orchextra.core.domain.entities.Proximity
import com.gigigo.orchextra.indoorpositioning.models.OxBeacon
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor
import java.util.Date

fun Beacon.toOxBeacon(): OxBeacon = with(this) {

  val hasTelemetryData = serviceUuid == 0xfeaa && extraDataFields.size > 0


  val oxBeacon = OxBeacon(
      hashcode = hashCode(),
      lastSeen = Date().time,
      lastMinuteSeen = Date().time / 1000 / 60,
      beaconAddress = bluetoothAddress,
      rssi = rssi,
      manufacturer = manufacturer,
      txPower = txPower,
      distance = distance,
      hasTelemetryData = hasTelemetryData)

  if (serviceUuid == 0xfeaa) { // This is an Eddystone beacon
    // Do we have telemetry data?
    if (extraDataFields.size > 0) {
      oxBeacon.hasTelemetryData = true
      oxBeacon.telemetryVersion = extraDataFields[0]
      oxBeacon.batteryMilliVolts = extraDataFields[1]
      oxBeacon.temperature = extraDataFields[2].toFloat()
      oxBeacon.pduCount = extraDataFields[3]
      oxBeacon.uptime = extraDataFields[4]
    } else {
      oxBeacon.hasTelemetryData = false
    }

    when (beaconTypeCode) {
      0x00 -> {
        oxBeacon.beaconType = OxBeacon.TYPE_EDDYSTONE_UID
        // This is a Eddystone-UID frame
        oxBeacon.namespaceId = id1.toString()
        oxBeacon.instanceId = id2.toString()
      }
      0x10 -> {
        oxBeacon.beaconType = OxBeacon.TYPE_EDDYSTONE_URL
        // This is a Eddystone-URL frame
        oxBeacon.url = UrlBeaconUrlCompressor.uncompress(id1.toByteArray())
      }
    }
  } else { // This is an iBeacon or ALTBeacon
    oxBeacon.beaconType = if (beaconTypeCode == 0xbeac) {
      OxBeacon.TYPE_ALTBEACON
    } else {
      OxBeacon.TYPE_IBEACON // 0x4c000215 is iBeacon
    }
    oxBeacon.uuid = id1.toString()
    oxBeacon.major = id2.toString()
    oxBeacon.minor = id3.toString()

  }

  return oxBeacon
}

fun OxBeacon.isInRegion(config: List<Proximity>): Boolean =
    config.any { it.uuid == this.uuid && it.major.toString() == this.major }