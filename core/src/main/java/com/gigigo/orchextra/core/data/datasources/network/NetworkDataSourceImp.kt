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

package com.gigigo.orchextra.core.data.datasources.network

import com.gigigo.orchextra.core.BuildConfig
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.data.datasources.network.interceptor.ErrorInterceptor
import com.gigigo.orchextra.core.data.datasources.network.interceptor.SessionInterceptor
import com.gigigo.orchextra.core.data.datasources.network.models.ApiGeoLocation
import com.gigigo.orchextra.core.data.datasources.network.models.ApiList
import com.gigigo.orchextra.core.data.datasources.network.models.ApiPoint
import com.gigigo.orchextra.core.data.datasources.network.models.toAction
import com.gigigo.orchextra.core.data.datasources.network.models.toApiAuthRequest
import com.gigigo.orchextra.core.data.datasources.network.models.toApiTokenData
import com.gigigo.orchextra.core.data.datasources.network.models.toApiTrigger
import com.gigigo.orchextra.core.data.datasources.network.models.toConfiguration
import com.gigigo.orchextra.core.data.datasources.network.models.toTokenData
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.OxPoint
import com.gigigo.orchextra.core.domain.entities.TokenData
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.exceptions.UnauthorizedException
import com.gigigo.orchextra.core.utils.LogUtils.LOGE
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkDataSourceImp(private val orchextra: Orchextra,
    private val sessionManager: SessionManager,
    private val dbDataSource: DbDataSource) : NetworkDataSource {

  private val orchextraCoreApi: OrchextraCoreApi
  private val orchextraTriggerApi: OrchextraTriggerApi

  init {

    val okHttpBuilder = OkHttpClient.Builder()
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
    okHttpBuilder.addInterceptor(ErrorInterceptor())
    okHttpBuilder.addInterceptor(SessionInterceptor(sessionManager))

    if (Orchextra.isDebuggable()) {
      val loggingInterceptor = HttpLoggingInterceptor()
      loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      okHttpBuilder.addInterceptor(loggingInterceptor)
    }

    val retrofitCore = Retrofit.Builder()
        .baseUrl(BuildConfig.CORE_API_URL)
        .client(okHttpBuilder.build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val retrofitTrigger = Retrofit.Builder()
        .baseUrl(BuildConfig.TRIGGER_API_URL)
        .client(okHttpBuilder.build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    orchextraCoreApi = retrofitCore.create(OrchextraCoreApi::class.java)
    orchextraTriggerApi = retrofitTrigger.create(OrchextraTriggerApi::class.java)
  }

  override fun getAuthentication(credentials: Credentials, forceNew: Boolean): String {

    return if (!forceNew && sessionManager.hasSession()) {
      sessionManager.getSession()

    } else {

      val apiClientResponse = orchextraCoreApi.getAuthentication(
          credentials.toApiAuthRequest()).execute().body()

      apiClientResponse?.data?.token ?: ""
    }
  }

  override fun getConfiguration(apiKey: String): Configuration {
    val apiResponse = orchextraCoreApi.getConfiguration(apiKey).execute().body()

    return apiResponse?.data?.toConfiguration() as Configuration
  }

  override fun getTriggerConfiguration(apiKey: String): Configuration {
    val apiResponse = orchextraTriggerApi.getConfiguration(apiKey).execute().body()

    return apiResponse?.data?.toConfiguration() as Configuration
  }

  override fun getTriggerList(point: OxPoint): Configuration {
    val request = ApiList(ApiGeoLocation(ApiPoint(lat = point.lat, lng = point.lng)))
    val apiResponse = makeCallWithRetry { orchextraTriggerApi.getList(request).execute().body() }

    return apiResponse?.data?.toConfiguration() as Configuration
  }

  override fun getAction(trigger: Trigger): Action {
    val apiResponse = makeCallWithRetry {
      orchextraTriggerApi.getAction(trigger.toApiTrigger()).execute().body()
    }

    return apiResponse?.data?.toAction() as Action
  }

  override fun confirmAction(id: String) {
    if (id == "-1") {
      LOGE("NetworkDataSourceImp", "Confirm action called without Id")
    } else {
      makeCallWithRetry { orchextraTriggerApi.confirmAction(id).execute().body() }
    }
  }

  override fun getTokenData(): TokenData {
    val apiResponse = makeCallWithRetry({
      orchextraCoreApi.getTokenData().execute().body()
    })

    return apiResponse?.data?.toTokenData() as TokenData
  }

  override fun updateTokenData(tokenData: TokenData): TokenData {
    val apiResponse = makeCallWithRetry {
      orchextraCoreApi.updateTokenData(tokenData.toApiTokenData()).execute().body()
    }

    return apiResponse?.data?.toTokenData() as TokenData
  }

  override fun unbindCrm() {
    makeCallWithRetry({
      val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"),
          "{\"crm\":null }")
      orchextraCoreApi.updateTokenData(requestBody).execute().body()
    })
  }

  private fun <T> makeCallWithRetry(call: () -> T?): T? {
    return if (sessionManager.hasSession()) {
      makeCallWithRetryOnSessionFailed(call)
    } else {
      val credentials = getAuthentication(orchextra.getCredentials(), true)
      sessionManager.saveSession(credentials)

      val crm = dbDataSource.getCrm()
      val device = dbDataSource.getDevice()
      updateTokenData(TokenData(crm = crm, device = device))

      call()
    }
  }

  private fun <T> makeCallWithRetryOnSessionFailed(call: () -> T?): T? {
    return try {
      call()
    } catch (exception: UnauthorizedException) {
      val credentials = getAuthentication(orchextra.getCredentials(), true)
      sessionManager.saveSession(credentials)

      val crm = dbDataSource.getCrm()
      val device = dbDataSource.getDevice()
      updateTokenData(TokenData(crm = crm, device = device))

      call()
    }
  }
}