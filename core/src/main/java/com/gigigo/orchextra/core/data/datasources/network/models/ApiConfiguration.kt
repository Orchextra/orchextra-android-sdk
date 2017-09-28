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
    val geoMarketing: List<ApiGeoMarketing>?,
    val proximity: List<ApiProximity>?,
    val eddystoneRegions: List<ApiEddystoneRegions>?,
    val customFields: Map<String, ApiCustomField>?)

data class ApiGeoMarketing(
    val code: String?,
    val point: ApiPoint?,
    val radius: Int?,
    val notifyOnEntry: Boolean?,
    val notifyOnExit: Boolean?,
    val stayTime: Int?)

data class ApiProximity(
    val code: String?,
    val uuid: String?,
    val minor: Int?,
    val major: Int?,
    val notifyOnEntry: Boolean?,
    val notifyOnExit: Boolean?)

data class ApiEddystoneRegions(
    val code: String?,
    val namespace: String?,
    val notifyOnEntry: Boolean?,
    val notifyOnExit: Boolean?)

data class ApiCustomField(
    val type: String?,
    val label: String?)