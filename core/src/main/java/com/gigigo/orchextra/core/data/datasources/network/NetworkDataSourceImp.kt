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
import com.gigigo.orchextra.core.data.datasources.network.models.ApiTokenData
import com.gigigo.orchextra.core.data.datasources.network.models.toAction
import com.gigigo.orchextra.core.data.datasources.network.models.toApiAuthRequest
import com.gigigo.orchextra.core.data.datasources.network.models.toApiOxDevice
import com.gigigo.orchextra.core.data.datasources.network.models.toConfiguration
import com.gigigo.orchextra.core.data.datasources.network.models.toOxType
import com.gigigo.orchextra.core.data.datasources.network.models.toTokenData
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.LoadConfiguration
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.entities.TokenData
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.exceptions.UnauthorizedException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkDataSourceImp(private val orchextra: Orchextra,
    private val sessionManager: SessionManager) : NetworkDataSource {

  private val orchextraApi: OrchextraApi

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

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpBuilder.build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    orchextraApi = retrofit.create(OrchextraApi::class.java)
  }

  override fun getAuthentication(credentials: Credentials): String {
    val apiClientResponse = orchextraApi.getAuthentication(
        credentials.toApiAuthRequest()).execute().body()

    return apiClientResponse?.data?.token ?: ""
  }

  override fun getConfiguration(loadConfiguration: LoadConfiguration): Configuration {
    val id = orchextra.getCredentials().apiKey

    val apiResponse = orchextraApi.getConfiguration(id).execute().body()

    return apiResponse?.data?.toConfiguration() as Configuration
  }

  override fun getAction(trigger: Trigger): Action {

    val apiResponse = makeCallWithRetry({ ->
      orchextraApi.getAction(trigger.type.toOxType(),
          value = trigger.value,
          event = trigger.event,
          phoneStatus = trigger.phoneStatus,
          distance = trigger.distance,
          temperature = "${trigger.temperature}",
          battery = "${trigger.battery}",
          uptime = "${trigger.uptime}")
          .execute().body()
    })

    return apiResponse?.data?.toAction() as Action
  }

  override fun confirmAction(id: String) {
    makeCallWithRetry({ ->
      orchextraApi.confirmAction(id).execute().body()
    })
  }

  override fun getTokenData(): TokenData {
    val apiResponse = makeCallWithRetry({ ->
      orchextraApi.getTokenData().execute().body()
    })

    return apiResponse?.toTokenData() as TokenData
  }

  override fun updateCrm(crm: OxCRM): OxCRM {
    return crm
  }

  override fun updateDevice(device: OxDevice): OxDevice {

    val apiResponse = makeCallWithRetry({ ->
      orchextraApi.updateTokenData(ApiTokenData(device = device.toApiOxDevice())).execute().body()
    })

    return apiResponse?.toTokenData()?.device as OxDevice
  }

  private fun <T> makeCallWithRetry(call: () -> T?): T? {
    return if (sessionManager.hasSession()) {
      makeCallWithRetryOnSessionFailed(call)
    } else {
      sessionManager.saveSession(getAuthentication(orchextra.getCredentials()))
      call()
    }
  }

  private fun <T> makeCallWithRetryOnSessionFailed(call: () -> T?): T? {
    return try {
      call()
    } catch (exception: UnauthorizedException) {
      sessionManager.saveSession(getAuthentication(orchextra.getCredentials()))
      call()
    }
  }
}