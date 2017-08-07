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
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import com.gigigo.orchextra.core.domain.entities.Token
import com.squareup.moshi.Moshi


class SessionManagerImp : SessionManager {

  private val sharedPreferences = Orchextra.getSharedPreferences()
  private var moshi = Moshi.Builder().build()
  private var tokenJsonAdapter = moshi.adapter(Token::class.java)
  private val TOKEN_KEY = "token_key"
  private var token: Token? = null

  @SuppressLint("CommitPrefEdits")
  override fun saveSession(token: Token) {
    this.token = token
    val editor = sharedPreferences.edit()
    editor.putString(TOKEN_KEY, tokenJsonAdapter.toJson(token))
    editor.commit()
  }

  override fun getSession(): Token {
    if (token != null) {

      return token as Token
    } else {

      val tokenJson = sharedPreferences.getString(TOKEN_KEY, "")
      token = tokenJsonAdapter.fromJson(tokenJson) ?: Token("", "", -1, "", "")

      return token as Token
    }
  }

  override fun hasSession(): Boolean {
    return getSession().value.isNotBlank()
  }

  @SuppressLint("CommitPrefEdits")
  override fun clearSession() {
    token = null
    val editor = sharedPreferences.edit()
    editor.putString(TOKEN_KEY, "")
    editor.commit()
  }
}