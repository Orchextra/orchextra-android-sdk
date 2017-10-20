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

package com.gigigo.orchextra.core.entities

import com.gigigo.orchextra.core.domain.entities.Schedule
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class ScheduleTest {

  @Test
  fun shouldBeAInvalidSchedule() {
    val schedule = Schedule()

    val isValid = schedule.isValid()

    isValid.shouldBeFalse()
  }

  @Test
  fun shouldBeAValidSchedule() {
    val schedule = Schedule(seconds = 18000)

    val isValid = schedule.isValid()

    isValid.shouldBeTrue()
  }
}