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

package com.gigigo.orchextra.core.domain.triggers

import android.content.Context
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.OrchextraErrorListener
import com.gigigo.orchextra.core.data.datasources.network.models.toError
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.imagerecognition.ImageRecognitionActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerType
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.GeoMarketing
import com.gigigo.orchextra.core.domain.entities.ImageRecognizerCredentials
import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.core.domain.entities.OxPoint
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.interactor.GetAction
import com.gigigo.orchextra.core.domain.interactor.GetTriggerConfiguration
import com.gigigo.orchextra.core.domain.interactor.GetTriggerList
import kotlin.properties.Delegates

class TriggerManager(
    private val getTriggerConfiguration: GetTriggerConfiguration,
    private val getTriggerList: GetTriggerList,
    private val getAction: GetAction,
    private val actionHandlerServiceExecutor: ActionHandlerServiceExecutor,
    private var errorListener: OrchextraErrorListener) : TriggerListener {

  var configuration: Configuration = Configuration()
  var point: OxPoint by Delegates.observable(OxPoint(0.0, 0.0)) { _, _, _ ->
    getTriggerList.get(
        point = point,
        onSuccess = {
          configuration = it
          initGeofenceTrigger()
          initIndoorPositioningTrigger()
        },
        onError = { errorListener.onError(it.toError()) })
  }
  var apiKey: String by Delegates.observable("") { _, _, _ ->
    getTriggerConfiguration.get(apiKey,
        onSuccess = {
          imageRecognizerCredentials = it.imageRecognizerCredentials
        },
        onError = { errorListener.onError(it.toError()) })
  }

  var scanner by Delegates.observable(VoidTrigger<Any>() as OxTrigger<ScannerType>)
  { _, _, new ->
    ScannerActionExecutor.scanner = new
  }

  var imageRecognizerCredentials: ImageRecognizerCredentials? = null

  var imageRecognizer by Delegates.observable(
      VoidTrigger<ImageRecognizerCredentials>() as OxTrigger<ImageRecognizerCredentials>)
  { _, _, new ->

    ImageRecognitionActionExecutor.imageRecognizer = new
    initImageRecognizer()
  }

  var geofence by Delegates.observable(
      VoidTrigger<List<GeoMarketing>>() as OxTrigger<List<GeoMarketing>>) { _, _, _ ->
    initGeofenceTrigger()
  }

  var indoorPositioning by Delegates.observable(
      VoidTrigger<List<IndoorPositionConfig>>() as OxTrigger<List<IndoorPositionConfig>>) { _, _, _ ->
    initIndoorPositioningTrigger()
  }

  private fun initImageRecognizer() {
    imageRecognizerCredentials?.let {
      imageRecognizer.setConfig(it)
    }
  }

  private fun initGeofenceTrigger() {
    if (configuration.geoMarketing.isNotEmpty()) {
      geofence.setConfig(configuration.geoMarketing)
      try {
        geofence.init()
      } catch (exception: SecurityException) {
        errorListener.onError(
            Error(code = Error.FATAL_ERROR, message = exception.message as String))
      }
    }
  }

  private fun initIndoorPositioningTrigger() {
    if (configuration.indoorPositionConfig.isNotEmpty()) {
      indoorPositioning.setConfig(configuration.indoorPositionConfig)
      try {
        indoorPositioning.init()
      } catch (exception: SecurityException) {
        errorListener.onError(
            Error(code = Error.FATAL_ERROR, message = exception.message as String))
      }
    }
  }

  fun finish() {
    scanner.finish()
    imageRecognizer.finish()
    geofence.finish()
    indoorPositioning.finish()
  }

  override fun onTriggerDetected(trigger: Trigger) = getAction.get(trigger,
      onSuccess = {
        actionHandlerServiceExecutor.execute(action = it)
      },
      onError = {
        errorListener.onError(it.toError())
      })

  companion object Factory {

    fun create(context: Context): TriggerManager {

      val networkDataSource = NetworkDataSource.create(context)

      return TriggerManager(
          GetTriggerConfiguration.create(networkDataSource),
          GetTriggerList.create(networkDataSource),
          GetAction.create(networkDataSource),
          ActionHandlerServiceExecutor.create(context),
          Orchextra)
    }
  }
}