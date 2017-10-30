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

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gigigo.orchextra.core.data.datasources.network.models.toError
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.customaction.CustomActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.customaction.CustomActionListener
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.datasources.SessionManager
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.IMAGE_RECOGNITION
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER
import com.gigigo.orchextra.core.domain.entities.Credentials
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.OxPoint
import com.gigigo.orchextra.core.domain.interactor.GetConfiguration
import com.gigigo.orchextra.core.domain.interactor.GetOxToken
import com.gigigo.orchextra.core.domain.triggers.TriggerManager
import com.gigigo.orchextra.core.utils.ActivityLifecycleManager
import com.gigigo.orchextra.core.utils.FileLogging
import com.gigigo.orchextra.core.utils.LocationProvider
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.PermissionsActivity
import java.util.concurrent.TimeUnit

object Orchextra : OrchextraErrorListener {

  private val TAG = LogUtils.makeLogTag(Orchextra::class.java)
  private var getOxToken: GetOxToken? = null
  private lateinit var triggerManager: TriggerManager
  private lateinit var actionHandlerServiceExecutor: ActionHandlerServiceExecutor
  private lateinit var locationProvider: LocationProvider
  private lateinit var dbDataSource: DbDataSource
  private var credentials: Credentials = Credentials()
  private var isReady = false
  private var isActivityRunning = false
  private var orchextraStatusListener: OrchextraStatusListener? = null
  private var orchextraErrorListener: OrchextraErrorListener? = null
  private var debuggable = false
  private var sessionManager: SessionManager? = null
  private var crmManager: CrmManager? = null
  var notificationActivityName: String? = null

  @JvmOverloads
  fun init(context: Application, apiKey: String, apiSecret: String, debuggable: Boolean = false) {

    this.debuggable = debuggable
    this.credentials = Credentials(apiKey = apiKey, apiSecret = apiSecret)
    this.triggerManager = TriggerManager.create(context)
    this.actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create()
    this.locationProvider = LocationProvider(context)
    this.sessionManager = SessionManager.create(context)
    this.crmManager = CrmManager.create(context, { onError(it) })
    this.triggerManager.apiKey = apiKey
    this.getOxToken = GetOxToken.create(NetworkDataSource.create(context))
    this.dbDataSource = DbDataSource.create(context)

    initLogger(context)

    ActivityLifecycleManager(app = context,
        onActivityResumed = { isActivityRunning = true },
        onActivityPaused = { isActivityRunning = false })

    PermissionsActivity.open(context, Manifest.permission.ACCESS_FINE_LOCATION,
        onSuccess = {
          getConfiguration(context, apiKey)
        },
        onError = {
          orchextraErrorListener?.onError(it.toError())
          changeStatus(false)
        })
  }

  private fun getConfiguration(context: Context, apiKey: String) {
    val getConfiguration = GetConfiguration.create(NetworkDataSource.create(context),
        dbDataSource)
    this.locationProvider.getLocation { point ->
      triggerManager.point = OxPoint(lat = point.lat, lng = point.lng)
    }

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

  fun setScanTime(scanTimeInSeconds: Long) {
    dbDataSource.saveScanTime(TimeUnit.SECONDS.toMillis(scanTimeInSeconds))
  }

  internal fun initLogger(context: Context) {
    LogUtils.fileLogging = FileLogging(context)
  }

  fun openScanner(context: Context) {
    checkInitialization()
    actionHandlerServiceExecutor.execute(context, Action(type = SCANNER))
  }

  fun openImageRecognition(context: Context) {
    checkInitialization()
    actionHandlerServiceExecutor.execute(context, Action(type = IMAGE_RECOGNITION))
  }

  fun getTriggerManager(): TriggerManager {
    checkInitialization()
    return triggerManager
  }

  private fun changeStatus(isReady: Boolean) {
    Orchextra.isReady = isReady
    orchextraStatusListener?.onStatusChange(isReady)
  }

  internal fun getCredentials(): Credentials = credentials

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

  fun setCustomActionListener(customActionListener: CustomActionListener) {
    CustomActionExecutor.getInstance().customActionListener = customActionListener
  }

  override fun onError(error: Error) {
    orchextraErrorListener?.onError(error)

    if (error.code == Error.FATAL_ERROR) {
      finish()
    }
  }

  fun getToken(tokenReceiver: OrchextraTokenReceiver) {
    getOxToken?.get(credentials,
        onSuccess = {
          tokenReceiver.onGetToken(it)
        },
        onError = {
          onError(it.toError())
        })
  }

  fun finish() {
    this.credentials = Credentials()
    this.triggerManager.finish()
    sessionManager?.clearSession()

    changeStatus(false)
  }

  fun setNotificationActivityClass(notificationActivityClass: Class<*>) {
    notificationActivityName = notificationActivityClass.canonicalName
  }

  fun getCrmManager(): CrmManager = crmManager as CrmManager

  internal fun isDebuggable(): Boolean = debuggable

  internal fun isActivityRunning() = isActivityRunning

  internal fun provideSharedPreferences(
      context: Context): SharedPreferences = context.getSharedPreferences(
      "orchextra",
      Context.MODE_PRIVATE)
}
