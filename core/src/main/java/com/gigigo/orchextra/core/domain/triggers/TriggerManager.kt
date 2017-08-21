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
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerActionExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.interactor.GetAction
import com.gigigo.orchextra.core.domain.interactor.GetAction.Callback
import kotlin.properties.Delegates

class TriggerManager(private val context: Context, private val getAction: GetAction,
    private val actionHandlerServiceExecutor: ActionHandlerServiceExecutor,
    private var orchextraErrorListener: OrchextraErrorListener) : TriggerListener {

  var configuration: Configuration = Configuration()

  var scanner by Delegates.observable(VoidScanner() as Scanner)
  { _, _, new ->
    ScannerActionExecutor.scanner = new
  }

  var geofence by Delegates.observable(VoidGeofence() as Geofence)
  { _, _, new ->
    if (configuration.geoMarketing.isNotEmpty()) {
      new.setGeoMarketingList(configuration.geoMarketing)

      try {
        new.init()
      } catch (exception: SecurityException) {
        orchextraErrorListener.onError(
            Error(code = Error.FATAL_ERROR, message = exception.message as String))
      }
    }
  }

  override fun onTriggerDetected(trigger: Trigger) {

    getAction.get(trigger, object : Callback {
      override fun onSuccess(action: Action) {
        actionHandlerServiceExecutor.execute(context = context, action = action)
      }

      override fun onError(error: NetworkException) {
        orchextraErrorListener.onError(error.toError())
      }
    })
  }

  companion object Factory {

    fun create(context: Context): TriggerManager = TriggerManager(context, GetAction.create(),
        ActionHandlerServiceExecutor.create(), Orchextra)
  }
}