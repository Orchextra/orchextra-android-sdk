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

package com.gigigo.orchextra.core.data.datasources.network.models

data class ApiConfiguration(
    val geofences: List<ApiGeofence>?,
    val beaconRegions: List<ApiRegion>?,
    val eddystoneRegions: List<ApiRegion>?,
    val customFields: Map<String, ApiCustomField>?,
    val requestWaitTime: Int?,
    val vuforia: ApiVuforia?)

data class ApiGeofence(
    val code: String?,
    val point: ApiPoint?,
    val radius: Int?,
    val notifyOnEntry: Boolean?,
    val notifyOnExit: Boolean?,
    val stayTime: Int?)

data class ApiRegion(
    val code: String?,
    val uuid: String?,
    val major: Int?,
    val namespace: String?,
    val notifyOnEntry: Boolean?,
    val notifyOnExit: Boolean?)

data class ApiCustomField(
    val type: String?,
    val label: String?)

data class ApiVuforia(
    val licenseKey: String?,
    val clientAccessKey: String?,
    val clientSecretKey: String?)