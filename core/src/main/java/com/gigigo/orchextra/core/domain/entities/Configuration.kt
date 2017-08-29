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

data class Configuration constructor(
    val geoMarketing: List<GeoMarketing> = listOf(),
    val proximity: List<Proximity> = listOf())


data class GeoMarketing constructor(
    val code: String,
    val point: Point,
    val radius: Int,
    val notifyOnEntry: Boolean,
    val notifyOnExit: Boolean,
    val stayTime: Int)

data class Point constructor(
    val lat: Double,
    val lng: Double)

data class Proximity constructor(
    val code: String,
    val uuid: String,
    val minor: Int,
    val major: Int,
    val notifyOnEntry: Boolean,
    val notifyOnExit: Boolean) : Parcelable {

  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      source.readInt(),
      source.readInt(),
      1 == source.readInt(),
      1 == source.readInt()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(code)
    writeString(uuid)
    writeInt(minor)
    writeInt(major)
    writeInt((if (notifyOnEntry) 1 else 0))
    writeInt((if (notifyOnExit) 1 else 0))
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Proximity> = object : Parcelable.Creator<Proximity> {
      override fun createFromParcel(source: Parcel): Proximity = Proximity(source)
      override fun newArray(size: Int): Array<Proximity?> = arrayOfNulls(size)
    }
  }
}