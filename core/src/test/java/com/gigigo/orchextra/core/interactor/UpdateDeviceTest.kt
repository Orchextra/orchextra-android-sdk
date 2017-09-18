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
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.interactor.UpdateDevice
import com.gigigo.orchextra.core.utils.PostExecutionThreadMock
import com.gigigo.orchextra.core.utils.ThreadExecutorMock
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Test

class UpdateDeviceTest {

  private val validDevice = OxDevice(apiKey = "apikey")
  private val invalidDevice = OxDevice()

  @Test
  fun shouldUpdateCrm() {
    val updateDevice = getUpdateDeviceInteractor()
    val onSuccess: (OxDevice) -> Unit = mock()
    val onError: (OxException) -> Unit = mock()

    updateDevice.update(device = validDevice,
        onSuccess = onSuccess,
        onError = onError)

    verify(onSuccess).invoke(validDevice)
    verifyZeroInteractions(onError)
  }

  @Test
  fun shouldFailAtUpdateCrm() {
    val error = NetworkException(-1, "")
    val updateDevice = getUpdateDeviceInteractor(error = error)
    val onSuccess: (OxDevice) -> Unit = mock()
    val onError: (OxException) -> Unit = mock()

    updateDevice.update(device = invalidDevice,
        onSuccess = onSuccess,
        onError = onError)

    verifyZeroInteractions(onSuccess)
    verify(onError).invoke(error)
  }

  private fun getUpdateDeviceInteractor(
      error: NetworkException = NetworkException(-1, "")): UpdateDevice {

    val networkDataSourceMock = mock<NetworkDataSource> {
      on { updateDevice(validDevice) } doReturn validDevice
      on { updateDevice(invalidDevice) } doThrow error
    }

    return UpdateDevice(ThreadExecutorMock(), PostExecutionThreadMock(), networkDataSourceMock)
  }
}