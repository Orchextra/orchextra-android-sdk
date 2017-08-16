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

package com.gigigo.orchextra.core.domain.entities

import android.os.Parcel
import android.os.Parcelable


enum class TriggerType {
  BEACON,
  BEACON_REGION,
  EDDYSTONE,
  GEOFENCE,
  QR,
  BARCODE,
  IMAGE_RECOGNITION;

  infix fun withValue(value: String) = Trigger(this, value)
}

data class Trigger constructor(
    val type: TriggerType,
    val value: String,
    val lat: Double? = null,
    val lng: Double? = null,
    val event: String? = null,
    val phoneStatus: String? = null,
    val distance: String? = null,
    val temperature: Double? = null,
    val battery: Double? = null,
    val uptime: Int? = null
) : Parcelable {
  constructor(source: Parcel) : this(
      TriggerType.values()[source.readInt()],
      source.readString(),
      source.readValue(Double::class.java.classLoader) as Double?,
      source.readValue(Double::class.java.classLoader) as Double?,
      source.readString(),
      source.readString(),
      source.readString(),
      source.readValue(Double::class.java.classLoader) as Double?,
      source.readValue(Double::class.java.classLoader) as Double?,
      source.readValue(Int::class.java.classLoader) as Int?
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(type.ordinal)
    writeString(value)
    writeValue(lat)
    writeValue(lng)
    writeString(event)
    writeString(phoneStatus)
    writeString(distance)
    writeValue(temperature)
    writeValue(battery)
    writeValue(uptime)
  }

  companion object {
    @JvmField
    @Suppress("unused")
    val CREATOR: Parcelable.Creator<Trigger> = object : Parcelable.Creator<Trigger> {
      override fun createFromParcel(source: Parcel): Trigger = Trigger(source)
      override fun newArray(size: Int): Array<Trigger?> = arrayOfNulls(size)
    }
  }
}