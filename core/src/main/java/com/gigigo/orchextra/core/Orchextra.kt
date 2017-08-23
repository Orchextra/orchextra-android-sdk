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
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.GeoLocation
import com.gigigo.orchextra.core.domain.entities.LoadConfiguration
import com.gigigo.orchextra.core.domain.entities.Point
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.interactor.GetConfiguration
import com.gigigo.orchextra.core.domain.triggers.TriggerManager
import com.gigigo.orchextra.core.utils.extensions.getAppData
import com.gigigo.orchextra.core.utils.extensions.getDeviceData
import java.lang.IllegalStateException

object Orchextra : OrchextraErrorListener {

  private var context: Context? = null
  private lateinit var triggerManager: TriggerManager
  private var credentials: Credentials = Credentials()
  private var isReady = false
  private var orchextraStatusListener: OrchextraStatusListener? = null
  private var orchextraErrorListener: OrchextraErrorListener? = null
  private val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create()

  fun init(context: Context, apiKey: String, apiSecret: String,
      orchextraStatusListener: OrchextraStatusListener? = null) {

    this.context = context
    this.orchextraStatusListener = orchextraStatusListener
    this.credentials = Credentials(apiKey = apiKey, apiSecret = apiSecret)
    this.triggerManager = TriggerManager.create(context)

    getConfiguration()
  }

  private fun getConfiguration() {

    val getConfiguration = GetConfiguration.create()

    val loadConfiguration = LoadConfiguration(
        app = context?.getAppData(),
        device = context?.getDeviceData(),
        geoLocation = GeoLocation(point = Point(lat = 40.4458471, lng = -3.6302917)))

    getConfiguration.get(loadConfiguration,
        object : GetConfiguration.Callback {
          override fun onSuccess(configuration: Configuration) {
            triggerManager.configuration = configuration

            changeStatus(true)
            println("getConfiguration onSuccess $configuration")
          }

          override fun onError(error: NetworkException) {
            changeStatus(false)
            println("getAuthentication onError: $error")
          }
        })
  }

  fun openScanner() {
    checkInitialization()
    actionHandlerServiceExecutor.execute(context as Context, Action(type = SCANNER))
  }

  fun getTriggerManager(): TriggerManager {
    checkInitialization()
    return triggerManager
  }

  private fun changeStatus(isReady: Boolean) {
    Orchextra.isReady = isReady
    orchextraStatusListener?.onStatusChange(isReady)
  }

  fun getCredentials(): Credentials = credentials

  fun isReady(): Boolean = Orchextra.isReady

  private fun checkInitialization() {
    if (!isReady) {
      throw IllegalStateException("You must call init()")
    }
  }

  fun setErrorListener(errorListener: OrchextraErrorListener) {
    this.orchextraErrorListener = errorListener
  }

  override fun onError(error: Error) {
    orchextraErrorListener?.onError(error)

    if (error.code == Error.FATAL_ERROR) {
      finish()
    }
  }

  fun finish() {
    this.context = null
    this.credentials = Credentials()

    changeStatus(false)
  }

  fun provideContext(): Context? = context

  fun provideSharedPreferences(): SharedPreferences? =
      provideContext()?.getSharedPreferences("orchextra", Context.MODE_PRIVATE)
}