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
import com.gigigo.orchextra.core.data.datasources.network.interceptor.ErrorInterceptor
import com.gigigo.orchextra.core.data.datasources.network.interceptor.SessionInterceptor
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.Token
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkDataSourceImp : NetworkDataSource {

  private val orchextraApi: OrchextraApi = ApiImp.getOrchextraApi()

  override fun getAuthentication(credentials: Credentials): Token {

    val apiCredintial = credentials.toApiAuthRequest("auth_sdk")

    val apiResponse = orchextraApi.getAuthentication(apiCredintial).execute()

    return apiResponse.body()?.data?.toToken() as Token
  }
}

private object ApiImp {

  fun getOrchextraApi(): OrchextraApi {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttpBuilder = OkHttpClient.Builder()
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
    okHttpBuilder.addInterceptor(ErrorInterceptor())
    okHttpBuilder.addInterceptor(SessionInterceptor())

    if (BuildConfig.NETWORK_LOG) {
      okHttpBuilder.addInterceptor(loggingInterceptor)
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(OrchextraApi::class.java)
  }
}