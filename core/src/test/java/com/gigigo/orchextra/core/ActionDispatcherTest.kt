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
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerType
import com.gigigo.orchextra.core.domain.actions.actionexecutors.webview.WebViewActionExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.BROWSER
import com.gigigo.orchextra.core.domain.entities.ActionType.CUSTOM_SCHEME
import com.gigigo.orchextra.core.domain.entities.ActionType.IMAGE_RECOGNITION
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER
import com.gigigo.orchextra.core.domain.entities.ActionType.WEBVIEW
import com.gigigo.orchextra.core.domain.entities.Notification
import com.gigigo.orchextra.core.domain.entities.Schedule
import com.gigigo.orchextra.core.domain.interactor.ConfirmAction
import com.gigigo.orchextra.core.schedule.ActionSchedulerManager
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ActionDispatcherTest {

  @Test
  fun shouldExecuteBrowserAction() {
    val browserActionExecutor: BrowserActionExecutor = mock()
    val actionDispatcher = getActionDispatcher(browserActionExecutor = browserActionExecutor)

    val action = Action(trackId = "test_123",
        type = BROWSER,
        url = "https://www.google.es")

    actionDispatcher.executeAction(action)

    verify(browserActionExecutor).open(action.url)
  }

  @Test
  fun shouldExecuteWebviewAction() {
    val webViewActionExecutor: WebViewActionExecutor = mock()
    val actionDispatcher = getActionDispatcher(webViewActionExecutor = webViewActionExecutor)

    val action = Action(trackId = "test_123",
        type = WEBVIEW,
        url = "https://www.google.es")

    actionDispatcher.executeAction(action)

    verify(webViewActionExecutor).open(action.url)
  }

//  @Test
//  fun shouldExecuteCustomSchemeAction() {
//    val customActionExecutor: CustomActionExecutor = mock()
//    val actionDispatcher = getActionDispatcher()
//
//    val action = Action(trackId = "test_123",
//        type = CUSTOM_SCHEME,
//        url = "https://www.google.es")
//
//    actionDispatcher.executeAction(action)
//
//    verify(customActionExecutor).onCustomSchema(action.url)
//  }

//  @Test
//  fun shouldExecutescannerAction() {
//    val scannerActionExecutor: ScannerActionExecutor = mock()
//    val actionDispatcher = getActionDispatcher()
//
//    val action = Action(trackId = "test_123",
//        type = SCANNER,
//        url = "https://www.google.es")
//
//    actionDispatcher.executeAction(action)
//
//    verify(scannerActionExecutor).open(ScannerType.SCANNER)
//  }

//  @Test
//  fun shouldExecuteImageRecognitionAction() {
//    val imageRecognitionActionExecutor: ImageRecognitionActionExecutor = mock()
//    val actionDispatcher = getActionDispatcher()
//
//    val action = Action(trackId = "test_123",
//        type = IMAGE_RECOGNITION,
//        url = "https://www.google.es")
//
//    actionDispatcher.executeAction(action)
//
//    verify(imageRecognitionActionExecutor).open()
//  }

  @Test
  fun shouldExecuteBrowserActionWithNotification() {
    val notificationActionExecutor: NotificationActionExecutor = mock()
    val actionDispatcher = getActionDispatcher(
        notificationActionExecutor = notificationActionExecutor)

    val action = Action(trackId = "test_123",
        type = BROWSER,
        url = "https://www.google.es",
        notification = Notification(title = "title", body = "body"))

    actionDispatcher.executeAction(action)

    verify(notificationActionExecutor).showNotification(eq(action.notification), any())
  }

  @Test
  fun shouldExecuteBrowserActionWithSchedule() {
    val actionSchedulerManager: ActionSchedulerManager = mock()
    val actionDispatcher = getActionDispatcher(
        actionSchedulerManager = actionSchedulerManager)

    val action = Action(trackId = "test_123",
        type = BROWSER,
        url = "https://www.google.es",
        schedule = Schedule(seconds = 60, cancelable = true))

    actionDispatcher.executeAction(action)

    verify(actionSchedulerManager).scheduleAction(action)
  }

  private fun getActionDispatcher(
      confirmAction: ConfirmAction = mock(),
      browserActionExecutor: BrowserActionExecutor = mock(),
      webViewActionExecutor: WebViewActionExecutor = mock(),
      notificationActionExecutor: NotificationActionExecutor = mock(),
      actionSchedulerManager: ActionSchedulerManager = mock()
  ): ActionDispatcher {

    return ActionDispatcher(confirmAction, browserActionExecutor, webViewActionExecutor,
        notificationActionExecutor, actionSchedulerManager)
  }
}