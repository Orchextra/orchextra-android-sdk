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

import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.EMPTY_CRM
import com.gigigo.orchextra.core.domain.entities.EMPTY_DEVICE
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.TokenData
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.interactor.UpdateCrm
import com.gigigo.orchextra.core.testutils.PostExecutionThreadMock
import com.gigigo.orchextra.core.testutils.ThreadExecutorMock
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Test
import java.util.Date

class UpdateCrmTest {

  private val validCrm = OxCRM(crmId = "id", gender = "f", birthDate = Date())
  private val validTokenData = TokenData(crm = validCrm, device = EMPTY_DEVICE)
  private val invalidCrm = EMPTY_CRM
  private val invalidTokenData = TokenData(crm = EMPTY_CRM, device = EMPTY_DEVICE)

  @Test
  fun shouldUpdateCrm() {
    val updateCrm = getUpdateCrmInteractor()
    val onSuccess: (OxCRM) -> Unit = mock()
    val onError: (OxException) -> Unit = mock()

    updateCrm.update(crm = validCrm,
        onSuccess = onSuccess,
        onError = onError)

    verify(onSuccess).invoke(validCrm)
    verifyZeroInteractions(onError)
  }

  @Test
  fun shouldFailAtUpdateCrm() {
    val error = NetworkException(-1, "")
    val updateCrm = getUpdateCrmInteractor(error = error)
    val onSuccess: (OxCRM) -> Unit = mock()
    val onError: (OxException) -> Unit = mock()

    updateCrm.update(crm = invalidCrm,
        onSuccess = onSuccess,
        onError = onError)

    verifyZeroInteractions(onSuccess)
    verify(onError).invoke(error)
  }

  private fun getUpdateCrmInteractor(
      error: NetworkException = NetworkException(-1, "")): UpdateCrm {

    val dbDataSource: DbDataSource = mock()

    val networkDataSourceMock = mock<NetworkDataSource> {
      on { updateTokenData(validTokenData) } doReturn validTokenData
      on { updateTokenData(invalidTokenData) } doThrow error
    }

    return UpdateCrm(ThreadExecutorMock(),
        PostExecutionThreadMock(), networkDataSourceMock,
        dbDataSource)
  }
}