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
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.entities.OxDevice
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.interactor.GetCrm
import com.gigigo.orchextra.core.utils.LogUtils

class CrmManager(private val getCrm: GetCrm,
    private var errorListener: OrchextraErrorListener) {

  var crm: OxCRM = OxCRM()
  var device: OxDevice = OxDevice()

  fun bindUser(user: OxCRM) {
    crm = user
  }

  fun unbindUser() {
    crm = OxCRM()
  }

  fun getCurrentUser(): OxCRM {

    getCrm.get(object : GetCrm.Callback {

      override fun onSuccess(crm: OxCRM) {
        LogUtils.LOGD("CrmManager", "data: $crm")
      }

      override fun onError(error: NetworkException) {
        errorListener.onError(error.toError())
      }
    })

    return crm
  }

  fun getAvailableCustomFields() {
    TODO("Not implemented")
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

  fun getDeviceTags(): List<String> = device.tags

  fun setDeviceTags(tags: List<String>) {
    device = device.copy(tags = tags)
  }

  fun getDeviceBussinesUnits(): List<String> = device.businessUnits

  fun setDeviceBussinesUnits(businessUnits: List<String>) {
    device = device.copy(businessUnits = businessUnits)
  }

  companion object Factory {

    fun create(): CrmManager = CrmManager(GetCrm.create(), Orchextra)
  }
}