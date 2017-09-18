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
import com.gigigo.orchextra.core.domain.entities.CustomField
import com.gigigo.orchextra.core.domain.entities.EMPTY_CRM
import com.gigigo.orchextra.core.domain.entities.EMPTY_DEVICE
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.interactor.GetTokenData
import com.gigigo.orchextra.core.domain.interactor.UpdateCrm
import com.gigigo.orchextra.core.domain.interactor.UpdateDevice

class CrmManager(private val apiKey: String, private val getTokenData: GetTokenData,
    private val updateCrm: UpdateCrm,
    private val updateDevice: UpdateDevice, private val showError: (error: Error) -> Unit) {

  var crm: OxCRM = EMPTY_CRM
  var device: OxDevice = EMPTY_DEVICE
  val onError: (OxException) -> Unit = { showError(it.toError()) }
  var availableCustomFields: List<CustomField> = listOf()

  fun bindUser(crm: OxCRM) {
    updateCrm.update(crm, onSuccess = { this.crm = it }, onError = onError)
  }

  fun unbindUser() {
    updateCrm.update(EMPTY_CRM, onSuccess = { this.crm = it }, onError = onError)
  }

  fun getCurrentUser(bind: (user: OxCRM) -> Unit) {
    if (crm.isNotEmpty()) {
      bind(crm)
    } else {
      updatedData { bind(crm) }
    }
  }

  fun getCustomFields(): Map<String, String> = crm.customFields

  fun setCustomFields(customFields: Map<String, String>) {
    crm = crm.copy(customFields = customFields)
  }

  fun updateCustomFields() {
    TODO("Not implemented")
  }

  fun getUserTags(): List<String> = crm.tags

  fun setUserTags(tags: List<String>) {
    crm = crm.copy(tags = tags)
  }

  fun getUserBussinesUnits(): List<String> = crm.businessUnits

  fun setUserBussinesUnits(businessUnits: List<String>) {
    crm = crm.copy(businessUnits = businessUnits)
  }

  fun getDevice(bind: (device: OxDevice) -> Unit) {
    if (device.isNotEmpty()) {
      bind(device)
    } else {
      updatedData { bind(device) }
    }
  }

  fun setDeviceTags(tags: List<String>, onSuccess: () -> Unit) {
    updateDevice.update(OxDevice(apiKey = apiKey, tags = tags), onSuccess = { onSuccess() },
        onError = onError)
  }

  fun setDeviceBussinesUnits(businessUnits: List<String>, onSuccess: () -> Unit) {
    updateDevice.update(OxDevice(apiKey = apiKey, businessUnits = businessUnits),
        onSuccess = { onSuccess() },
        onError = onError)
  }

  private fun updatedData(onUpdate: () -> Unit) {
    getTokenData.get(onSuccess = {
      crm = it.crm
      device = it.device
      onUpdate()
    }, onError = onError)
  }

  companion object Factory {

    fun create(apiKey: String, showError: (error: Error) -> Unit): CrmManager = CrmManager(
        apiKey,
        GetTokenData.create(),
        UpdateCrm.create(),
        UpdateDevice.create(),
        showError)
  }
}