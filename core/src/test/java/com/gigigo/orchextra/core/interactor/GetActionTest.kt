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

package com.gigigo.orchextra.core.interactor

import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.NOTHING
import com.gigigo.orchextra.core.domain.entities.TriggerType.GEOFENCE
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.interactor.GetAction
import com.gigigo.orchextra.core.testutils.PostExecutionThreadMock
import com.gigigo.orchextra.core.testutils.ThreadExecutorMock
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Test

class GetActionTest {

  private val validTrigger = GEOFENCE withValue "test"
  private val invalidTrigger = VOID withValue "-1"

  @Test
  fun shouldGetValidAction() {
    val action = Action(type = NOTHING)
    val getAction = getGetActionInteractor(action = action)
    val onSuccess: (Action) -> Unit = mock()
    val onError: (OxException) -> Unit = mock()

    getAction.get(trigger = validTrigger,
        onSuccess = onSuccess,
        onError = onError)

    verify(onSuccess).invoke(action)
    verifyZeroInteractions(onError)
  }

  @Test
  fun shouldGetError() {
    val error = NetworkException(-1, "")
    val getAction = getGetActionInteractor(error = error)
    val onSuccess: (Action) -> Unit = mock()
    val onError: (OxException) -> Unit = mock()

    getAction.get(trigger = invalidTrigger,
        onSuccess = onSuccess,
        onError = onError)

    verifyZeroInteractions(onSuccess)
    verify(onError).invoke(error)
  }

  private fun getGetActionInteractor(action: Action = Action(type = NOTHING),
      error: NetworkException = NetworkException(-1, "")): GetAction {

    val networkDataSourceMock = mock<NetworkDataSource> {
      on { getAction(validTrigger) } doReturn action
      on { getAction(invalidTrigger) } doThrow error
    }

    return GetAction(ThreadExecutorMock(),
        PostExecutionThreadMock(), networkDataSourceMock)
  }
}