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
import com.gigigo.orchextra.core.data.datasources.network.models.ApiConfiguration
import com.gigigo.orchextra.core.data.datasources.network.models.ApiList
import com.gigigo.orchextra.core.data.datasources.network.models.ApiTrigger
import com.gigigo.orchextra.core.data.datasources.network.models.OxResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrchextraTriggerApi {

  @GET("configuration/{id}")
  fun getConfiguration(@Path("id") id: String): Call<OxResponse<ApiConfiguration>>

  @POST("list")
  fun getList(@Body listData: ApiList): Call<OxResponse<ApiConfiguration>>

  @POST("action")
  fun getAction(@Body trigger: ApiTrigger): Call<OxResponse<ApiAction>>

  @POST("action/confirm/{id}")
  fun confirmAction(@Path("id") id: String): Call<OxResponse<ApiAction>>
}