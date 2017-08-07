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

package com.gigigo.orchextra.core

import android.content.Context
import android.content.SharedPreferences
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.interactor.GetAuthentication

object Orchextra {

  private var context: Context? = null
  private var apiKey: String? = null
  private var apiSecret: String? = null

  fun init(context: Context, apiKey: String, apiSecret: String) {

    this.context = context
    this.apiKey = apiKey
    this.apiSecret = apiSecret

    val getAuthentication = GetAuthentication.create()
    getAuthentication.getAuthentication(Credentials(apiKey = apiKey, apiSecret = apiSecret),
        object : GetAuthentication.Callback {
          override fun onSuccess() {
            println("getAuthentication onSuccess")
          }

          override fun onError(error: NetworkException) {
            println("getAuthentication onError: $error")
          }
        })
  }

  fun finish() {
    this.context = null
    this.apiKey = null
    this.apiSecret = null
  }

  fun getSharedPreferences(): SharedPreferences {
    return context!!.getSharedPreferences("orchextra", Context.MODE_PRIVATE)
  }
}