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

data class Notification constructor(
    val title: String = "",
    val body: String = "") : Parcelable {

  fun isEmpty(): Boolean {
    return title.isBlank() && body.isBlank()
  }

  fun isNotEmpty(): Boolean {
    return !isEmpty()
  }

  constructor(source: Parcel) : this(
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(title)
    writeString(body)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Notification> = object : Parcelable.Creator<Notification> {
      override fun createFromParcel(source: Parcel): Notification = Notification(source)
      override fun newArray(size: Int): Array<Notification?> = arrayOfNulls(size)
    }
  }
}