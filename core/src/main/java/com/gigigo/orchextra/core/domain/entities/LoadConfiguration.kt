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

data class LoadConfiguration constructor(
    val app: AppData? = null,
    val device: DeviceData? = null,
    val geoLocation: GeoLocation? = null)

data class AppData constructor(
    val appVersion: String,
    val buildVersion: String,
    val bundleId: String)

data class DeviceData constructor(
    var handset: String,
    var osVersion: String,
    var language: String,
    var timeZone: String,
    var instanceId: String,
    var secureId: String,
    var serialNumber: String,
    var bluetoothMacAddress: String,
    var wifiMacAddress: String,
    var tags: List<String>,
    var businessUnits: List<String>)

data class GeoLocation constructor(
    var country: String = "",
    var countryCode: String = "",
    var locality: String = "",
    var zip: String = "",
    var street: String = "",
    var point: Point)