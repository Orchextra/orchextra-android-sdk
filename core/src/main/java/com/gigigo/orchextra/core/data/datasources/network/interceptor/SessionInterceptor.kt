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

import com.gigigo.orchextra.core.BuildConfig
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.util.*


private const val UNAUTHORIZED = 401

class SessionInterceptor(private val sessionManager: SessionManager) : Interceptor {


    override fun intercept(chain: Chain): Response {

        val requestBuilder = chain.request().newBuilder()
            .addHeader("X-orx-version", "ANDROID_${BuildConfig.VERSION_NAME}")
            .addHeader("Accept-Language", Locale.getDefault().toString())
            .addHeader("Content-Type", "application/json")

        if (sessionManager.hasSession()) {
            requestBuilder.addHeader("Authorization", "JWT ${sessionManager.getSession()}")
        }

        val request = requestBuilder.build()

        val response = chain.proceed(request)

        if (response.code == UNAUTHORIZED) {
            sessionManager.clearSession()
        }

        return response
    }
}