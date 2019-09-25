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

package com.gigigo.orchextra.core.data.datasources.session

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.gigigo.orchextra.core.domain.datasources.SessionManager

class SessionManagerImp(private val sharedPreferences: SharedPreferences) : SessionManager {

    private val TOKEN_KEY = "token_key"
    private var token = ""

    @SuppressLint("CommitPrefEdits")
    override fun saveSession(token: String) {
        this.token = token
        val editor = sharedPreferences.edit()
        editor?.putString(TOKEN_KEY, token)
        editor?.commit()
    }

    override fun getSession(): String {
        return if (token.isNotEmpty()) {
            token
        } else {
            sharedPreferences.getString(TOKEN_KEY, "") ?: ""
        }
    }

    override fun hasSession(): Boolean = getSession().isNotEmpty()

    @SuppressLint("CommitPrefEdits")
    override fun clearSession() {
        token = ""
        val editor = sharedPreferences.edit()
        editor?.putString(TOKEN_KEY, token)
        editor?.apply()
    }
}