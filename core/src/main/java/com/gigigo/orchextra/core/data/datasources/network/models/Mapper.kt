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

import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.GeoMarketing
import com.gigigo.orchextra.core.domain.entities.Point
import com.gigigo.orchextra.core.domain.entities.Token
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.squareup.moshi.Moshi
import okhttp3.Response as OkResponse


fun Credentials.toApiAuthRequest(grantType: String): ApiAuthRequest =
    with(this) {
      ApiAuthRequest(grantType,
          ApiCredentials(apiKey,
              apiSecret))
    }

fun ApiToken.toToken(): Token =
    with(this) {
      Token(value ?: "", type ?: "", expiresIn ?: -1L, expiresAt ?: "", projectId ?: "")
    }

fun OkResponse.parseError(): ApiError =
    with(this) {
      val moshi = Moshi.Builder().build()
      val errorJsonAdapter = moshi.adapter(Response::class.java)
      val response = errorJsonAdapter.fromJson(body()?.string())
      return response.error ?: ApiError(
          -1, "")
    }

fun ApiError.toNetworkException(): NetworkException =
    with(this) {
      return NetworkException(code ?: -1, message ?: "")
    }

fun ApiConfiguration.toConfiguration(): Configuration =
    with(this) {
      return Configuration(geoMarketing.toGeoMarketingList())
    }

fun List<ApiGeoMarketing>.toGeoMarketingList(): List<GeoMarketing> = map {
  it.toGeoMarketing()
}

fun ApiGeoMarketing.toGeoMarketing(): GeoMarketing =
    with(this) {
      return GeoMarketing(code = code,
          point = point.toPoint(),
          radius = radius,
          notifyOnEntry = notifyOnEntry,
          notifyOnExit = notifyOnExit,
          stayTime = stayTime)
    }

fun ApiPoint.toPoint(): Point =
    with(this) {
      return Point(lat, lng)
    }