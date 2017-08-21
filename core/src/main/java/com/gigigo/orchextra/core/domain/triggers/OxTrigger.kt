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

package com.gigigo.orchextra.core.domain.triggers

import com.gigigo.orchextra.core.domain.entities.Trigger

interface OxTrigger<in T> {

  fun init()

  fun setConfig(config: T)

  fun finish()
}

class VoidTrigger<in T> : OxTrigger<T> {
  override fun init() {
    throw NotImplementedError("Operation is not implemented")
  }

  override fun setConfig(config: T) {
    throw NotImplementedError("Operation is not implemented")
  }

  override fun finish() {
    throw NotImplementedError("Operation is not implemented")
  }

}

interface TriggerListener {
  fun onTriggerDetected(trigger: Trigger)
}