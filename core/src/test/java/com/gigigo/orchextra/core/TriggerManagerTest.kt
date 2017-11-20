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

import com.gigigo.orchextra.core.data.datasources.network.models.toError
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.WEBVIEW
import com.gigigo.orchextra.core.domain.entities.TriggerType.BARCODE
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.interactor.GetAction
import com.gigigo.orchextra.core.domain.interactor.GetTriggerConfiguration
import com.gigigo.orchextra.core.domain.interactor.GetTriggerList
import com.gigigo.orchextra.core.domain.triggers.TriggerManager
import com.gigigo.orchextra.core.testutils.PostExecutionThreadMock
import com.gigigo.orchextra.core.testutils.ThreadExecutorMock
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test


class TriggerManagerTest {

  private val TEST_SUCCESS_TRIGGER = QR withValue "test_123"
  private val TEST_ERROR_TRIGGER = BARCODE withValue "test_error_123"
  private val TEST_ACTION = Action(trackId = "test_123", type = WEBVIEW,
      url = "https://www.google.es")
  private val TEST_NETWORK_EXCEPTION = NetworkException(-1, "error")

  @Test
  fun shouldExecuteAction() {
    val actionHandlerServiceExecutor: ActionHandlerServiceExecutor = mock()
    val triggerManager = getTriggerManager(
        actionHandlerServiceExecutor = actionHandlerServiceExecutor)

    triggerManager.onTriggerDetected(TEST_SUCCESS_TRIGGER)

    verify(actionHandlerServiceExecutor).execute(eq(TEST_ACTION))
  }

  @Test
  fun shouldNotifyGetActionError() {
    val orchextraErrorListener: OrchextraErrorListener = mock()
    val triggerManager = getTriggerManager(orchextraErrorListener = orchextraErrorListener)

    triggerManager.onTriggerDetected(TEST_ERROR_TRIGGER)

    verify(orchextraErrorListener).onError(eq(TEST_NETWORK_EXCEPTION.toError()))
  }

  private fun getTriggerManager(actionHandlerServiceExecutor: ActionHandlerServiceExecutor = mock(),
      orchextraErrorListener: OrchextraErrorListener = mock()): TriggerManager {

    val networkDataSource = mock<NetworkDataSource> {
      on { getAction(TEST_SUCCESS_TRIGGER) } doReturn TEST_ACTION
      on { getAction(TEST_ERROR_TRIGGER) } doThrow TEST_NETWORK_EXCEPTION
    }

    val getTriggerList = GetTriggerList(ThreadExecutorMock(),
        PostExecutionThreadMock(), networkDataSource)
    val getTriggerConfiguration = GetTriggerConfiguration(ThreadExecutorMock(),
        PostExecutionThreadMock(), networkDataSource)
    val getAction = GetAction(ThreadExecutorMock(),
        PostExecutionThreadMock(), networkDataSource)

    return TriggerManager(
        getTriggerConfiguration = getTriggerConfiguration,
        getTriggerList = getTriggerList,
        getAction = getAction,
        actionHandlerServiceExecutor = actionHandlerServiceExecutor,
        errorListener = orchextraErrorListener)
  }
}