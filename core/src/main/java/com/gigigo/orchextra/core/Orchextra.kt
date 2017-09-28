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
import android.content.SharedPreferences
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.OxPoint
import com.gigigo.orchextra.core.domain.interactor.GetConfiguration
import com.gigigo.orchextra.core.domain.triggers.TriggerManager
import com.gigigo.orchextra.core.utils.ActivityLifecycleManager
import com.gigigo.orchextra.core.utils.FileLogging
import com.gigigo.orchextra.core.utils.LocationProvider
import com.gigigo.orchextra.core.utils.LogUtils
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
  private var sessionManager: SessionManager? = null
  private var crmManager: CrmManager? = null

  @JvmOverloads
  fun init(context: Application, apiKey: String, apiSecret: String, debuggable: Boolean = false) {

    this.context = context
    this.debuggable = debuggable
    this.credentials = Credentials(apiKey = apiKey, apiSecret = apiSecret)
    this.triggerManager = TriggerManager.create(context)
    this.actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create()
    this.locationProvider = LocationProvider(context)
    this.locationProvider.getLocation { point ->
      triggerManager.point = OxPoint(lat = point.lat, lng = point.lng)
    }
    this.sessionManager = SessionManager.create(Orchextra.provideContext())
    this.crmManager = CrmManager.create { onError(it) }

    initLogger(context)

    ActivityLifecycleManager(app = context,
        onActivityResumed = { isActivityRunning = true },
        onActivityPaused = { isActivityRunning = false })

    getConfiguration(apiKey)
  }

  private fun getConfiguration(apiKey: String) {
    val getConfiguration = GetConfiguration.create()

    getConfiguration.get(apiKey,
        onSuccess = {
          crmManager?.availableCustomFields = it.customFields
          changeStatus(true)
        },
        onError = {
          changeStatus(false)
          LogUtils.LOGE(TAG, "getConfiguration: ${it.error}")
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

  fun setStatusListener(orchextraStatusListener: OrchextraStatusListener) {
    this.orchextraStatusListener = orchextraStatusListener
  }

  fun removeStatusListener() {
    this.orchextraStatusListener = null
  }

  fun setErrorListener(errorListener: OrchextraErrorListener) {
    this.orchextraErrorListener = errorListener
  }

  fun removeErrorListener() {
    this.orchextraErrorListener = null
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
    sessionManager?.clearSession()

    changeStatus(false)
  }

  fun setContext(context: Application) {
    this.context = context
  }

  fun getCrmManager(): CrmManager = crmManager as CrmManager

  fun isDebuggable(): Boolean = debuggable

  fun isActivityRunning() = isActivityRunning

  fun provideContext(): Context = context as Context

  fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
      "orchextra",
      Context.MODE_PRIVATE)
}
