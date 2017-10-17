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

import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import com.gigigo.orchextra.core.domain.interactor.ValidateTrigger
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.Test

class ValidateTriggerTest {

  @Test
  fun shouldBeAValidQRTrigger() {
    val trigger = Trigger(QR, "e21dguywqbjs")
    val validateTrigger = getValidateTrigger()

    val validTrigger = validateTrigger.validate(trigger)

    validTrigger.shouldEqual(trigger)
  }

  @Test
  fun shouldBeAValidBeaconTriggerWithDistanceNear() {
    val trigger = Trigger(BEACON, "e21dguywqbjs", distance = "near")
    val validateTrigger = getValidateTrigger(trigger.copy(detectedTime = 0L))

    val validTrigger = validateTrigger.validate(trigger)

    validTrigger.distance.shouldEqual("near")
  }

  @Test
  fun shouldBeAInvalidBeaconTrigger() {
    val trigger = Trigger(BEACON, "e21dguywqbjs", distance = "near")
    val validateTrigger = getValidateTrigger(trigger)

    val validTrigger = validateTrigger.validate(trigger.copy(detectedTime = 0L))

    validTrigger.isVoid().shouldBeTrue()
  }

  private fun getValidateTrigger(savedTrigger: Trigger = Trigger(VOID, "")): ValidateTrigger {

    val dbDataSource = mock<DbDataSource> {
      on { getTrigger(any()) } doReturn savedTrigger
      on { getWaitTime() } doReturn 120000L
    }

    return ValidateTrigger(mock(), mock(), dbDataSource, Orchextra)
  }
}