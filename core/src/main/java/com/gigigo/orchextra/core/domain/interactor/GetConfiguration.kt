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

package com.gigigo.orchextra.core.domain.interactor

import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.LoadConfiguration
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor

class GetConfiguration : Runnable {

  private val networkDataSource = NetworkDataSource.create()
  private var loadConfiguration: LoadConfiguration? = null
  private var callback: Callback? = null

  fun get(loadConfiguration: LoadConfiguration, callback: Callback) {
    this.loadConfiguration = loadConfiguration
    this.callback = callback

    ThreadExecutor.execute(this)
  }

  override fun run() {
    try {
      val configuration = networkDataSource.getConfiguration(loadConfiguration as LoadConfiguration)

      notifySuccess(configuration)
    } catch (error: NetworkException) {
      notifyError(error)
    }
  }

  private fun notifySuccess(configuration: Configuration) {
    PostExecutionThread.execute(Runnable { callback?.onSuccess(configuration) })
  }

  private fun notifyError(error: NetworkException) {
    PostExecutionThread.execute(Runnable { callback?.onError(error) })
  }

  interface Callback {

    fun onSuccess(configuration: Configuration)

    fun onError(error: NetworkException)
  }

  companion object Factory {

    fun create(): GetConfiguration = GetConfiguration()
  }
}