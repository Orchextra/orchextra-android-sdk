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

data class Configuration(
    val geoMarketing: List<GeoMarketing> = listOf(),
    val indoorPositionConfig: List<IndoorPositionConfig> = listOf(),
    val customFields: List<CustomField> = listOf(),
    val requestWaitTime: Int? = 0,
    var imageRecognizerCredentials: ImageRecognizerCredentials? = null)

data class GeoMarketing(
    val code: String,
    val point: Point,
    val radius: Int,
    val notifyOnEntry: Boolean,
    val notifyOnExit: Boolean,
    val stayTime: Int)

data class Point(
    val lat: Double,
    val lng: Double)

data class CustomField(
    val key: String,
    val type: String,
    val label: String)

data class ImageRecognizerCredentials(
    val licenseKey: String? = "",
    val clientAccessKey: String? = "",
    val clientSecretKey: String? = ""): Parcelable {
  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readString())

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(licenseKey)
    parcel.writeString(clientAccessKey)
    parcel.writeString(clientSecretKey)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<ImageRecognizerCredentials> {
    override fun createFromParcel(parcel: Parcel): ImageRecognizerCredentials {
      return ImageRecognizerCredentials(parcel)
    }

    override fun newArray(size: Int): Array<ImageRecognizerCredentials?> {
      return arrayOfNulls(size)
    }
  }
}