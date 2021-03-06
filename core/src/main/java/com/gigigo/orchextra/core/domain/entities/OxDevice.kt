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

val EMPTY_DEVICE = OxDevice("")

data class OxDevice(
    val deviceId: String,
    val secureId: String? = null,
    val serialNumber: String? = null,
    val bluetoothMacAddress: String? = null,
    val wifiMacAddress: String? = null,
    val clientApp: OxClientApp? = null,
    val notificationPush: OxNotificationPush? = null,
    val device: OxDeviceInfo? = null,
    val tags: List<String>? = null,
    val businessUnits: List<String>? = null) {

  fun isEmpty(): Boolean = this == EMPTY_DEVICE

  fun isNotEmpty(): Boolean = !isEmpty()
}

class OxDeviceInfo(
    val timeZone: String?,
    val osVersion: String?,
    val language: String?,
    val handset: String?,
    val type: String?)

class OxClientApp(
    val bundleId: String,
    val buildVersion: String,
    val appVersion: String,
    val sdkVersion: String,
    val sdkDevice: String)

class OxNotificationPush(
    val senderId: String,
    val token: String)