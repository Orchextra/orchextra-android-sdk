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
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.gigigo.orchextra.core.data.datasources.network.NetworkDataSourceImp
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.LoadConfiguration
import com.gigigo.orchextra.core.utils.readTextAndClose
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NetworkDataSourceImpTest {

  private lateinit var server: MockWebServer

  @Before
  fun setUp() {
    server = MockWebServer()
    server.start()
  }

  @Test
  fun shouldGetConfiguration() {
    val fileName = "configuration_401_unauthorized.json"
    server.enqueue(MockResponse()
        .setResponseCode(200)
        .setBody(getStringFromFile(fileName)))
    val networkDataSourceImp = getNetworkDataSourceImp()
    val loadConfiguration = LoadConfiguration()

    val configuration = networkDataSourceImp.getConfiguration(loadConfiguration)

    print(configuration)
  }

  @After
  fun tearDown() {
    server.shutdown()
  }

  private fun getStringFromFile(filePath: String): String {
    val stream = getContext().resources.assets.open(filePath)
    val ret = stream.readTextAndClose()
    stream.close()
    return ret
  }

  private fun getNetworkDataSourceImp(): NetworkDataSourceImp {

    val orchextra = mock<Orchextra> {
      on { getCredentials() } doReturn Credentials()
    }

    val sessionManager = mock<SessionManager> {
      on { hasSession() } doReturn true
    }

    return NetworkDataSourceImp(orchextra = orchextra, sessionManager = sessionManager)
  }

  private fun getContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext
}