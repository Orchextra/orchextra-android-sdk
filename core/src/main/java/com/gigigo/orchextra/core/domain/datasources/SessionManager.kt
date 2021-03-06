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

package com.gigigo.orchextra.core.domain.datasources

import android.content.Context
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.data.datasources.session.SessionManagerImp

interface SessionManager {

  fun saveSession(token: String)

  fun getSession(): String

  fun hasSession(): Boolean

  fun clearSession()

  companion object Factory {

    var sessionManager: SessionManager? = null

    fun create(context: Context): SessionManager {

      if (sessionManager == null) {
        val sharedPreferences = Orchextra.provideSharedPreferences(context)
        sessionManager = SessionManagerImp(sharedPreferences)
      }

      return sessionManager as SessionManager
    }
  }
}