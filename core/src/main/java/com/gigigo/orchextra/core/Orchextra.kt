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

import android.app.Application
import android.content.Context
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
import com.gigigo.orchextra.core.utils.ActivityLifecycleManager
import com.gigigo.orchextra.core.utils.ActivityLifecycleManager.ActivityLifecycleCallback
import com.gigigo.orchextra.core.utils.FileLogging
import com.gigigo.orchextra.core.utils.LocationProvider
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.extensions.getAppData
import com.gigigo.orchextra.core.utils.extensions.getDeviceData
import java.lang.IllegalStateException

object Orchextra : OrchextraErrorListener {

  private val TAG = LogUtils.makeLogTag(Orchextra::class.java)
  private var context: Application? = null
  private lateinit var triggerManager: TriggerManager
  private lateinit var actionHandlerServiceExecutor: ActionHandlerServiceExecutor
  private lateinit var locationProvider: LocationProvider
  private var credentials: Credentials = Credentials()
  private var isReady = false
  private var isActivityRunning = false
  private var orchextraStatusListener: OrchextraStatusListener? = null
  private var orchextraErrorListener: OrchextraErrorListener? = null
  private var debuggable = false

  @JvmOverloads
  fun init(context: Application, apiKey: String, apiSecret: String,
      orchextraStatusListener: OrchextraStatusListener? = null, debuggable: Boolean = false) {

    this.context = context
    this.debuggable = debuggable
    this.orchextraStatusListener = orchextraStatusListener
    this.credentials = Credentials(apiKey = apiKey, apiSecret = apiSecret)
    this.triggerManager = TriggerManager.create(context)
    this.actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create()
    this.locationProvider = LocationProvider(context)
    this.locationProvider.getLocation { point -> getConfiguration(point) }

    initLogger(context)

    ActivityLifecycleManager(context,
        object : ActivityLifecycleCallback {
          override fun onActivityResumed() {
            isActivityRunning = true
          }

          override fun onActivityPaused() {
            isActivityRunning = false
          }
        })
  }

  private fun getConfiguration(point: Point) {

    val loadConfiguration = LoadConfiguration(
        app = context?.getAppData(),
        device = context?.getDeviceData(),
        geoLocation = GeoLocation(point = point))

    val getConfiguration = GetConfiguration.create()
    getConfiguration.get(loadConfiguration,
        object : GetConfiguration.Callback {
          override fun onSuccess(configuration: Configuration) {
            triggerManager.configuration = configuration

            changeStatus(true)
          }

          override fun onError(error: NetworkException) {
            changeStatus(false)
            LogUtils.LOGE(TAG, "getConfiguration: ${error.error}")
          }
        })
  }

  fun initLogger(context: Context) {
    LogUtils.fileLogging = FileLogging(context)
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

  fun setOrchextraStatusListener(orchextraStatusListener: OrchextraStatusListener) {
    this.orchextraStatusListener = orchextraStatusListener
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
    this.triggerManager.finish()

    changeStatus(false)
  }

  fun setContext(context: Application) {
    this.context = context
  }

  fun isDebuggable(): Boolean = debuggable

  fun isActivityRunning() = isActivityRunning

  fun provideContext(): Context = context as Context
}
