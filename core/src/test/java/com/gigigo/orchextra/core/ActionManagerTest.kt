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

import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.BROWSER
import com.gigigo.orchextra.core.domain.entities.Notification
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.domain.interactor.GetAction
import com.gigigo.orchextra.core.utils.PostExecutionThreadMock
import com.gigigo.orchextra.core.utils.ThreadExecutorMock
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test


class ActionManagerTest {

  @Test
  fun shouldExecuteBrowserAction() {
    val actionDispatcher: ActionDispatcher = mock()
    val action = Action(trackId = "test_123",
        type = BROWSER,
        url = "https://www.google.es",
        notification = Notification())
    val actionManager = getActionManager(action, actionDispatcher)

    actionManager.onTriggerDetected(QR withValue "test_123")

    verify(actionDispatcher).executeAction(action)
  }


  private fun getActionManager(action: Action, actionDispatcher: ActionDispatcher): ActionManager {
    val networkDataSource = mock<NetworkDataSource> {
      on { getAction(any()) } doReturn action
    }
    val getAction = GetAction(ThreadExecutorMock(), PostExecutionThreadMock(), networkDataSource)

    return ActionManager(actionDispatcher = actionDispatcher, getAction = getAction)
  }
}