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

package com.gigigo.orchextra.core.actions

import com.gigigo.orchextra.core.actions.actionexecutors.browser.BrowserActionExecutor
import com.gigigo.orchextra.core.actions.actionexecutors.customaction.CustomActionExecutor
import com.gigigo.orchextra.core.actions.actionexecutors.imagerecognition.ImageRecognitionActionExecutor
import com.gigigo.orchextra.core.actions.actionexecutors.notification.NotificationActionExecutor
import com.gigigo.orchextra.core.actions.actionexecutors.scanner.ScannerActionExecutor
import com.gigigo.orchextra.core.actions.actionexecutors.webview.WebViewActionExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.BROWSER
import com.gigigo.orchextra.core.domain.entities.ActionType.CUSTOM_SCHEME
import com.gigigo.orchextra.core.domain.entities.ActionType.IMAGE_RECOGNITION
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER
import com.gigigo.orchextra.core.domain.entities.ActionType.WEBVIEW

class ActionDispatcher constructor(private val browserActionExecutor: BrowserActionExecutor,
    private val webViewActionExecutor: WebViewActionExecutor,
    private val customActionExecutor: CustomActionExecutor,
    private val scannerActionExecutor: ScannerActionExecutor,
    private val imageRecognitionActionExecutor: ImageRecognitionActionExecutor,
    private val notificationActionExecutor: NotificationActionExecutor) {

  fun executeAction(action: Action) = with(action) {

    if (hasNotification()) {
      notificationActionExecutor.showNotification(notification)
    }

    when (type) {
      BROWSER -> browserActionExecutor.open(url)
      WEBVIEW -> webViewActionExecutor.open(url)
      CUSTOM_SCHEME -> customActionExecutor.open(url)
      SCANNER -> scannerActionExecutor.open()
      IMAGE_RECOGNITION -> imageRecognitionActionExecutor.open(url)
    }
  }

  companion object Factory {

    fun create(): ActionDispatcher = ActionDispatcher(
        BrowserActionExecutor.create(),
        WebViewActionExecutor.create(),
        CustomActionExecutor.create(),
        ScannerActionExecutor,
        ImageRecognitionActionExecutor.create(),
        NotificationActionExecutor.create())
  }
}