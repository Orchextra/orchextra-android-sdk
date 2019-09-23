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

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.gigigo.orchextra.core.data.datasources.session.SessionManagerImp
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.Test

class SessionManagerTest {

  private val TOKEN_KEY = "token_key"

  @Test
  fun shouldSaveSession() {
    val editorMock: Editor = mock()
    val token = "test"
    val sessionManager: SessionManager = getSessionManager(editor = editorMock)

    sessionManager.saveSession(token)

    verify(editorMock).putString(TOKEN_KEY, token)
    verify(editorMock).commit()
  }

  @Test
  fun shouldGetSessionFromPreferences() {
    val token = "test"
    val sessionManager: SessionManager = getSessionManager(token)

    val currentToken = sessionManager.getSession()

    currentToken.shouldEqual(token)
  }

  @Test
  fun shouldClearSession() {
    val editorMock: Editor = mock()
    val sessionManager: SessionManager = getSessionManager(editor = editorMock)
    val token = ""

    sessionManager.clearSession()

    verify(editorMock).putString(TOKEN_KEY, token)
    verify(editorMock).apply()
  }

  @Test
  fun shouldHasValidSession() {
    val token = "test"
    val sessionManager: SessionManager = getSessionManager(token)

    val hasSession = sessionManager.hasSession()

    hasSession.shouldBeTrue()
  }

  private fun getSessionManager(token: String = "", editor: Editor = mock()): SessionManager {

    val sharedPreferencesMock = mock<SharedPreferences> {
      on { edit() } doReturn editor
      on { getString(TOKEN_KEY, "") } doReturn token
    }

    return SessionManagerImp(sharedPreferencesMock)
  }
}