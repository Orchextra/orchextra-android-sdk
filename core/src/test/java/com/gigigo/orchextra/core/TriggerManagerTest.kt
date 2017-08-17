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

import com.gigigo.orchextra.core.domain.actions.ActionDispatcher
import com.gigigo.orchextra.core.domain.actions.actionexecutors.browser.BrowserActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.customaction.CustomActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.imagerecognition.ImageRecognitionActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.notification.NotificationActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerActionExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.webview.WebViewActionExecutor
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.BROWSER
import com.gigigo.orchextra.core.domain.entities.ActionType.WEBVIEW
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.domain.interactor.GetAction
import com.gigigo.orchextra.core.domain.triggers.TriggerManager
import com.gigigo.orchextra.core.schedule.ActionSchedulerManager
import com.gigigo.orchextra.core.utils.PostExecutionThreadMock
import com.gigigo.orchextra.core.utils.ThreadExecutorMock
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test


class TriggerManagerTest {

  private val TEST_TRIGGER = QR withValue "test_123"

  @Test
  fun shouldExecuteBrowserAction() {
    val browserActionExecutor: BrowserActionExecutor = mock()
    val action = Action(trackId = "test_123",
        type = BROWSER,
        url = "https://www.google.es")
    val actionManager = getActionManager(action, browserActionExecutor = browserActionExecutor)

    actionManager.onTriggerDetected(TEST_TRIGGER)

    verify(browserActionExecutor).open(action.url)
  }

  @Test
  fun shouldExecuteWebViewAction() {
    val webViewActionExecutor: WebViewActionExecutor = mock()
    val action = Action(trackId = "test_123",
        type = WEBVIEW,
        url = "https://www.google.es")
    val actionManager = getActionManager(action, webViewActionExecutor = webViewActionExecutor)

    actionManager.onTriggerDetected(TEST_TRIGGER)

    verify(webViewActionExecutor).open(action.url)
  }

  private fun getActionManager(action: Action,
      browserActionExecutor: BrowserActionExecutor = mock(),
      webViewActionExecutor: WebViewActionExecutor = mock(),
      customActionExecutor: CustomActionExecutor = mock(),
      scannerActionExecutor: ScannerActionExecutor = mock(),
      imageRecognitionActionExecutor: ImageRecognitionActionExecutor = mock(),
      notificationActionExecutor: NotificationActionExecutor = mock(),
      orchextraErrorListener: OrchextraErrorListener = mock(),
      actionSchedulerManager: ActionSchedulerManager = mock()
  ): TriggerManager {

    val networkDataSource = mock<NetworkDataSource> {
      on { getAction(any()) } doReturn action
    }

    val actionDispatcher = ActionDispatcher(browserActionExecutor, webViewActionExecutor,
        customActionExecutor, scannerActionExecutor, imageRecognitionActionExecutor,
        notificationActionExecutor, actionSchedulerManager)
    val getAction = GetAction(ThreadExecutorMock(), PostExecutionThreadMock(), networkDataSource)

    return TriggerManager(getAction = getAction, actionDispatcher = actionDispatcher,
        orchextraErrorListener = orchextraErrorListener)
  }
}