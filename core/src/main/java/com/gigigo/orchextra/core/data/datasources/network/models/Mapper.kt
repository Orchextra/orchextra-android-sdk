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

import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.CustomField
import com.gigigo.orchextra.core.domain.entities.EMPTY_CRM
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.GeoMarketing
import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.core.domain.entities.Notification
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.entities.Point
import com.gigigo.orchextra.core.domain.entities.Schedule
import com.gigigo.orchextra.core.domain.entities.TokenData
import com.gigigo.orchextra.core.domain.entities.TriggerType
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.exceptions.UnauthorizedException
import com.squareup.moshi.Moshi
import okhttp3.Response as OkResponse


fun Credentials.toApiAuthRequest(): ApiAuthRequest = with(this) {
  ApiAuthRequest(apiKey, apiSecret)
}

fun OkResponse.parseError(): ApiError = with(this) {
  val moshi = Moshi.Builder().build()
  val errorJsonAdapter = moshi.adapter(OxErrorResponse::class.java)
  val response = errorJsonAdapter.fromJson(body()?.string())
  return response.error ?: ApiError(-1, "")
}

fun ApiError.toNetworkException(): NetworkException = with(this) {
  return NetworkException(code ?: -1, message ?: "")
}

fun ApiError.toUnauthorizedException(): UnauthorizedException = with(this) {
  return UnauthorizedException(code ?: -1, message ?: "")
}

fun ApiConfiguration.toConfiguration(): Configuration = with(this) {

  val proximity = proximity?.map { it.toIndoorPositionConfig() } ?: listOf()
  val eddystoneRegions = eddystoneRegions?.map { it.toIndoorPositionConfig() } ?: listOf()
  val indoorPositionConfig = proximity + eddystoneRegions

  return Configuration(
      geoMarketing = geoMarketing?.toGeoMarketingList() ?: listOf(),
      indoorPositionConfig = indoorPositionConfig,
      customFields = customFields?.toCustomFieldList() ?: listOf())
}

fun List<ApiGeoMarketing>.toGeoMarketingList(): List<GeoMarketing> = map {
  it.toGeoMarketing()
}

fun ApiGeoMarketing.toGeoMarketing(): GeoMarketing = with(this) {
  return GeoMarketing(code = code ?: "",
      point = point?.toPoint() ?: Point(-1.0, -1.0),
      radius = radius ?: -1,
      notifyOnEntry = notifyOnEntry ?: false,
      notifyOnExit = notifyOnExit ?: false,
      stayTime = stayTime ?: -1)
}

fun Map<String, ApiCustomField>.toCustomFieldList(): List<CustomField> {
  val list: MutableList<CustomField> = ArrayList()
  for ((key, value) in this) {
    list.add(CustomField(key = key, type = value.type ?: "",
        label = value.label ?: ""))
  }
  return list
}

fun ApiProximity.toIndoorPositionConfig(): IndoorPositionConfig =
    with(this) {
      return IndoorPositionConfig(
          code = code ?: "",
          uuid = uuid ?: "",
          minor = minor ?: -1,
          major = major ?: -1,
          notifyOnEntry = notifyOnEntry ?: false,
          notifyOnExit = notifyOnExit ?: false)
    }

fun ApiEddystoneRegions.toIndoorPositionConfig(): IndoorPositionConfig = with(this) {
  return IndoorPositionConfig(
      code = code ?: "",
      namespace = namespace ?: "",
      notifyOnEntry = notifyOnEntry ?: false,
      notifyOnExit = notifyOnExit ?: false)
}

fun ApiPoint.toPoint(): Point = with(this) {
  return Point(lat, lng)
}

fun ApiAction.toAction(): Action = with(this) {
  return Action(
      trackId = trackId ?: "",
      type = ActionType.fromOxType(type ?: ""),
      url = url ?: "",
      notification = notification?.toNotification() ?: Notification(),
      schedule = schedule?.toSchedule() ?: Schedule())
}

fun ApiNotification.toNotification(): Notification =
    with(this) {
      return Notification(
          title = title ?: "",
          body = body ?: "")
    }

fun ApiSchedule.toSchedule(): Schedule = with(this) {
  return Schedule(
      seconds = seconds ?: -1,
      cancelable = cancelable ?: true)
}

fun OxException.toError(): Error = with(this) {
  return Error(
      code = code,
      message = error)
}

fun ApiTokenData.toTokenData(): TokenData = with(this) {
  return TokenData(crm = crm?.toOxCrm() ?: EMPTY_CRM,
      device = device?.toOxDevice() ?: OxDevice())
}

fun OxCRM.toOxCrm(): ApiOxCrm = with(this) {
  return ApiOxCrm(
      crmId = crmId,
      gender = gender,
      birthDate = birthDate,
      tags = tags,
      businessUnits = businessUnits,
      customFields = customFields)
}

fun ApiOxCrm.toOxCrm(): OxCRM = with(this) {
  return OxCRM(
      crmId = crmId ?: "ERROR",
      gender = gender,
      birthDate = birthDate,
      tags = tags,
      businessUnits = businessUnits,
      customFields = customFields)
}

fun ApiOxDevice.toOxDevice(): OxDevice = with(this) {
  return OxDevice(
      tags = tags ?: ArrayList(),
      businessUnits = businessUnits ?: ArrayList())
}

fun TriggerType.toOxType(): String = when (this) {
  TriggerType.BEACON -> "beacon"
  TriggerType.BEACON_REGION -> "beacon_region"
  TriggerType.EDDYSTONE -> "eddystone"
  TriggerType.GEOFENCE -> "geofence"
  TriggerType.QR -> "qr"
  TriggerType.BARCODE -> "barcode"
  TriggerType.IMAGE_RECOGNITION -> "vuforia"
  TriggerType.VOID -> "void"
}