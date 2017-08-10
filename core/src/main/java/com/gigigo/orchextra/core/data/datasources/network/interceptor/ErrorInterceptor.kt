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

package com.gigigo.orchextra.core.data.datasources.network.interceptor

import com.gigigo.orchextra.core.data.datasources.network.models.parseError
import com.gigigo.orchextra.core.data.datasources.network.models.toNetworkException
import com.gigigo.orchextra.core.data.datasources.network.models.toUnauthorizedException
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response


class ErrorInterceptor : Interceptor {

  override fun intercept(chain: Chain): Response {

    val response = chain.proceed(chain.request())

    if (!response.isSuccessful) {

      val error = response.parseError()

      if (error.code == 401) {
        throw error.toUnauthorizedException()
      } else {
        throw error.toNetworkException()
      }
    }

    return response
  }
}