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

import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.core.domain.entities.TriggerType
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON
import com.gigigo.orchextra.core.domain.entities.TriggerType.EDDYSTONE
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.utils.HASH
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
      hasTelemetryData = hasTelemetryData,
      lastDetection = Date())

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

fun OxBeacon.isInRegion(config: List<IndoorPositionConfig>): Boolean =
    config.any {
      (it.uuid.isNotEmpty() && it.uuid == this.uuid && it.major.toString() == this.major)
          || it.namespace.isNotEmpty() && it.namespace == this.namespaceId.replace("0x", "")
    }

fun OxBeacon.isSameRegion(beacon: OxBeacon): Boolean = with(this) {
  (this.uuid.isNotEmpty() && this.uuid == beacon.uuid && this.major == beacon.major)
      || this.namespaceId.isNotEmpty() && this.namespaceId.replace("0x",
      "") == this.namespaceId.replace("0x", "")
}

fun OxBeacon.getType(): TriggerType = when (this.beaconType) {
  OxBeacon.TYPE_EDDYSTONE_UID -> EDDYSTONE
  OxBeacon.TYPE_EDDYSTONE_URL -> EDDYSTONE
  OxBeacon.TYPE_ALTBEACON -> BEACON
  OxBeacon.TYPE_IBEACON -> BEACON
  else -> BEACON
}

fun OxBeacon.getValue(): String {
  return if (this.uuid.isEmpty()) {
    "${this.namespaceId.replace("0x", "")}${this.instanceId.replace("0x", "")}"
  } else {
    HASH.md5("${this.uuid.toUpperCase()}_${this.major}_${this.minor}")
  }
}