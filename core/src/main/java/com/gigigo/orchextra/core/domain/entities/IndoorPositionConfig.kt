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

data class IndoorPositionConfig constructor(
    val code: String = "",
    val uuid: String = "",
    val namespace: String = "",
    val instanceId: String = "",
    val minor: Int = -1,
    val major: Int = -1,
    val notifyOnEntry: Boolean = true,
    val notifyOnExit: Boolean = true) : Parcelable {

  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
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
    writeString(namespace)
    writeString(instanceId)
    writeInt(minor)
    writeInt(major)
    writeInt((if (notifyOnEntry) 1 else 0))
    writeInt((if (notifyOnExit) 1 else 0))
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<IndoorPositionConfig> = object : Parcelable.Creator<IndoorPositionConfig> {
      override fun createFromParcel(source: Parcel): IndoorPositionConfig = IndoorPositionConfig(
          source)

      override fun newArray(size: Int): Array<IndoorPositionConfig?> = arrayOfNulls(size)
    }
  }
}