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
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.GeoMarketing
import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.core.domain.entities.OxPoint
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.interactor.GetAction
import com.gigigo.orchextra.core.domain.interactor.GetTriggerConfig
import com.gigigo.orchextra.core.domain.interactor.ValidateTrigger
import kotlin.properties.Delegates

class TriggerManager(private val context: Context, private val getTriggerConfig: GetTriggerConfig,
    private val getAction: GetAction, private val validateTrigger: ValidateTrigger,
    private val actionHandlerServiceExecutor: ActionHandlerServiceExecutor,
    private var orchextraErrorListener: OrchextraErrorListener) : TriggerListener {

  var configuration: Configuration = Configuration()
  var point: OxPoint by Delegates.observable(OxPoint(0.0, 0.0)) { _, _, _ ->
    getTriggerConfig.get(
        point = point,
        onSuccess = {
          configuration = it
          initTrigger()
        },
        onError = { orchextraErrorListener.onError(it.toError()) })
  }

  var scanner by Delegates.observable(VoidTrigger<Any>() as OxTrigger<Any>)
  { _, _, new ->
    ScannerActionExecutor.scanner = new
  }

  var imageRecognizer by Delegates.observable(VoidTrigger<Any>() as OxTrigger<Any>)
  { _, _, newValue ->
    ImageRecognitionActionExecutor.imageRecognizer = newValue
  }


  var geofence by Delegates.observable(
      VoidTrigger<List<GeoMarketing>>() as OxTrigger<List<GeoMarketing>>) { _, _, _ ->
    initTrigger()
  }

  var indoorPositioning by Delegates.observable(
      VoidTrigger<List<IndoorPositionConfig>>() as OxTrigger<List<IndoorPositionConfig>>) { _, _, _ ->
    initTrigger()
  }

  private fun initTrigger() {
    if (configuration.geoMarketing.isNotEmpty()) {
      geofence.setConfig(configuration.geoMarketing)
      try {
        geofence.init()
      } catch (exception: SecurityException) {
        orchextraErrorListener.onError(
            Error(code = Error.FATAL_ERROR, message = exception.message as String))
      }
    }

    if (configuration.indoorPositionConfig.isNotEmpty()) {
      indoorPositioning.setConfig(configuration.indoorPositionConfig)
      try {
        indoorPositioning.init()
      } catch (exception: SecurityException) {
        orchextraErrorListener.onError(
            Error(code = Error.FATAL_ERROR, message = exception.message as String))
      }
    }
  }

  fun finish() {
    scanner.finish()
    geofence.finish()
    indoorPositioning.finish()
  }

  override fun onTriggerDetected(trigger: Trigger) = validateTrigger.validate(trigger,
      onSuccess = {
        if (!it.isVoid()) {
          getActionByTrigger(it)
        }
      },
      onError = {
        if (it.toError().isValid()) {
          orchextraErrorListener.onError(it.toError())
        }
      })

  private fun getActionByTrigger(trigger: Trigger) = getAction.get(trigger,
      onSuccess = { actionHandlerServiceExecutor.execute(context = context, action = it) },
      onError = { orchextraErrorListener.onError(it.toError()) })

  companion object Factory {

    fun create(context: Context): TriggerManager = TriggerManager(context,
        GetTriggerConfig.create(), GetAction.create(),
        ValidateTrigger.create(), ActionHandlerServiceExecutor.create(), Orchextra)
  }
}