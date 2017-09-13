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

import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.data.datasources.network.NetworkDataSourceImp
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.LoadConfiguration
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.entities.Token
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.exceptions.NetworkException

interface NetworkDataSource {

  @Throws(NetworkException::class)
  fun getAuthentication(credentials: Credentials): Token

  @Throws(NetworkException::class)
  fun getConfiguration(loadConfiguration: LoadConfiguration): Configuration

  @Throws(NetworkException::class)
  fun getAction(trigger: Trigger): Action

  @Throws(NetworkException::class)
  fun confirmAction(id: String)

  @Throws(NetworkException::class)
  fun getCrm(): OxCRM

  @Throws(NetworkException::class)
  fun getDevice(): OxDevice

  @Throws(NetworkException::class)
  fun updateCrm(crm: OxCRM): OxCRM

  companion object Factory {

    var networkDataSource: NetworkDataSource? = null

    fun create(): NetworkDataSource {

      if (networkDataSource == null) {
        networkDataSource = NetworkDataSourceImp(Orchextra,
            SessionManager.create(Orchextra.provideContext()))
      }

      return networkDataSource as NetworkDataSource
    }
  }
}