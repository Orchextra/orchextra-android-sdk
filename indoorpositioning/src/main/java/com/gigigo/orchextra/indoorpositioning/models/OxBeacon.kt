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

import android.os.Parcel
import android.os.Parcelable

data class OxBeacon(
    var hashcode: Int = 0, // hashcode()
    var beaconType: Int = 0, // Eddystone, altBeacon, iBeacon
    var beaconAddress: String = "", // MAC address of the bluetooth emitter
    var uuid: String = "",
    var major: String = "",
    var minor: String = "",
    var txPower: Int = 0,
    var rssi: Int = 0,
    var distance: Double = 0.toDouble(),
    var lastSeen: Long = 0,
    var lastMinuteSeen: Long = 0,
    var manufacturer: Int = 0,
    var url: String = "",
    var namespaceId: String = "",
    var instanceId: String = "",
    var hasTelemetryData: Boolean = false,
    var telemetryVersion: Long = 0,
    var batteryMilliVolts: Long = 0,
    var temperature: Float = 0F,
    var pduCount: Long = 0,
    var uptime: Long = 0) : Parcelable {


  fun getTemperatureInCelsius(): Float {
    val tmp = temperature / 256F

    if (tmp == (1 shl 7).toFloat()) { // 0x8000
      return 0F
    }
    return if (tmp > (1 shl 7)) tmp - (1 shl 8) else tmp
  }

  fun getDistanceQualifier(): String = when {
    distance < 0.5 -> "immediate"
    distance < 5 -> "near"
    distance < 20 -> "far"
    else -> "unknown"
  }

  constructor(source: Parcel) : this(
      source.readInt(),
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readInt(),
      source.readInt(),
      source.readDouble(),
      source.readLong(),
      source.readLong(),
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      1 == source.readInt(),
      source.readLong(),
      source.readLong(),
      source.readFloat(),
      source.readLong(),
      source.readLong()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(hashcode)
    writeInt(beaconType)
    writeString(beaconAddress)
    writeString(uuid)
    writeString(major)
    writeString(minor)
    writeInt(txPower)
    writeInt(rssi)
    writeDouble(distance)
    writeLong(lastSeen)
    writeLong(lastMinuteSeen)
    writeInt(manufacturer)
    writeString(url)
    writeString(namespaceId)
    writeString(instanceId)
    writeInt((if (hasTelemetryData) 1 else 0))
    writeLong(telemetryVersion)
    writeLong(batteryMilliVolts)
    writeFloat(temperature)
    writeLong(pduCount)
    writeLong(uptime)
  }

  companion object {
    const val TYPE_EDDYSTONE_UID = 0

    const val TYPE_EDDYSTONE_URL = 1

    const val TYPE_ALTBEACON = 2

    const val TYPE_IBEACON = 3

    @JvmField
    val CREATOR: Parcelable.Creator<OxBeacon> = object : Parcelable.Creator<OxBeacon> {
      override fun createFromParcel(source: Parcel): OxBeacon = OxBeacon(source)
      override fun newArray(size: Int): Array<OxBeacon?> = arrayOfNulls(size)
    }
  }
}