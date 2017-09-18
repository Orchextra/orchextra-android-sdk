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

package com.gigigo.orchextra.core.domain.entities

val EMPTY_CRM = OxCRM("EMPTY")


data class OxCRM @JvmOverloads constructor(
    val crmId: String,
    val gender: String? = null,
    val birthDate: String? = null,
    val tags: List<String>? = null,
    val businessUnits: List<String>? = null,
    val customFields: Map<String, String>? = null) {

  fun isEmpty(): Boolean = this == EMPTY_CRM

  fun isNotEmpty(): Boolean = !isEmpty()
}
