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

import com.gigigo.orchextra.core.data.datasources.network.models.ApiAction
import com.gigigo.orchextra.core.data.datasources.network.models.ApiAuthRequest
import com.gigigo.orchextra.core.data.datasources.network.models.ApiConfiguration
import com.gigigo.orchextra.core.data.datasources.network.models.ApiToken
import com.gigigo.orchextra.core.data.datasources.network.models.ApiTokenData
import com.gigigo.orchextra.core.data.datasources.network.models.OxResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrchextraApi {

  @POST("/token")
  fun getAuthentication(@Body apiAuthRequest: ApiAuthRequest): Call<OxResponse<ApiToken>>

  //  @POST("v1/configuration")
  @GET("/configuration/{id}")
      //  fun getConfiguration(@Body loadConfiguration: LoadConfiguration): Call<OxResponse<ApiConfiguration>>
  fun getConfiguration(@Path("id") id: String): Call<OxResponse<ApiConfiguration>>

  @GET("v1/action")
  fun getAction(@Query("type") type: String,
      @Query("value") value: String,
      @Query("event") event: String?,
      @Query("phoneStatus") phoneStatus: String?,
      @Query("distance") distance: String?,
      @Query("temperature") temperature: String?,
      @Query("battery") battery: String?,
      @Query("uptime") uptime: String?): Call<OxResponse<ApiAction>>

  @POST("v1/action/confirm/{id}")
  fun confirmAction(@Path("id") id: String): Call<OxResponse<ApiAction>>

  @GET("/token/data")
  fun getTokenData(): Call<ApiTokenData>

  @PUT("/token/data")
  fun updateTokenData(@Body apiTokenData: ApiTokenData): Call<ApiTokenData>
}