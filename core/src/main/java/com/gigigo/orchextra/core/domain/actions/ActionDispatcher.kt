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

package com.gigigo.orchextra.core.domain.actions

import android.content.Context
import android.util.Log
import com.gigigo.orchextra.core.domain.actions.actionexecutors.browser.BrowserActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.customaction.CustomActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.imagerecognition.ImageRecognitionActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.notification.NotificationActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScanCodeActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerType
import com.gigigo.orchextra.core.domain.actions.actionexecutors.webview.WebViewActionExecutor
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.BROWSER
import com.gigigo.orchextra.core.domain.entities.ActionType.CUSTOM_SCHEME
import com.gigigo.orchextra.core.domain.entities.ActionType.IMAGE_RECOGNITION
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER_WITHOUT_ACTION
import com.gigigo.orchextra.core.domain.entities.ActionType.SCAN_CODE
import com.gigigo.orchextra.core.domain.entities.ActionType.WEBVIEW
import com.gigigo.orchextra.core.domain.interactor.ConfirmAction
import com.gigigo.orchextra.core.schedule.ActionSchedulerManager

class ActionDispatcher constructor(
    private val confirmAction: ConfirmAction,
    private val browserActionExecutor: BrowserActionExecutor,
    private val webViewActionExecutor: WebViewActionExecutor,
    private val notificationActionExecutor: NotificationActionExecutor,
    private val actionSchedulerManager: ActionSchedulerManager) {

  fun executeAction(action: Action) {

    if (action.hasSchedule()) {
      scheduleAction(action)
    } else {
      dispatchAction(action)
    }
  }

  private fun scheduleAction(action: Action) {
    actionSchedulerManager.scheduleAction(action)
  }

  private fun dispatchAction(action: Action) {

    confirmAction.get(action.trackId)

    if (action.hasNotification()) {
      notificationActionExecutor.showNotification(action.notification, action)
    } else {
      openActionView(action)
    }
  }

  private fun openActionView(action: Action) = with(action) {
    when (type) {
      BROWSER -> browserActionExecutor.open(url)
      WEBVIEW -> webViewActionExecutor.open(url)
      CUSTOM_SCHEME -> CustomActionExecutor.onCustomSchema(url)
      SCANNER -> ScannerActionExecutor.open(ScannerType.SCANNER)
      IMAGE_RECOGNITION -> ImageRecognitionActionExecutor.open()
      SCANNER_WITHOUT_ACTION -> ScannerActionExecutor.open(ScannerType.SCANNER_WITHOUT_ACTION)
      SCAN_CODE -> ScanCodeActionExecutor.onCustomSchema(url)
      else -> Log.d(TAG, "openActionView()")
    }
  }

  companion object Factory {
    private const val TAG = "ActionDispatcher"

    fun create(context: Context): ActionDispatcher {

      val networkDataSource = NetworkDataSource.create(context)

      return ActionDispatcher(
          ConfirmAction.create(networkDataSource),
          BrowserActionExecutor.create(context),
          WebViewActionExecutor.create(context),
          NotificationActionExecutor.create(context),
          ActionSchedulerManager.create(context))
    }
  }
}