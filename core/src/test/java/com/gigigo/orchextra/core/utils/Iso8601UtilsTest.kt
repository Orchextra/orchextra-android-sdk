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

package com.gigigo.orchextra.core.utils

import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class Iso8601UtilsTest {

  @Test
  fun shouldParseADate() {
    val stringDate = "2017-09-21T00:00:00.000Z"
    val expectedDate = newDate(2017, 9, 21, 0, 0, 0, 0, 0)

    val date = Iso8601Utils.parse(stringDate)

    date.shouldEqual(expectedDate)
  }

  @Test
  fun shouldFormatADate() {
    val expectedString = "2017-09-21T00:00:00.000Z"
    val testDate = newDate(2017, 9, 21, 0, 0, 0, 0, 0)

    val date = Iso8601Utils.format(testDate)

    date.shouldEqual(expectedString)
  }

  private fun newDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int,
      millis: Int,
      offset: Int): Date {
    val calendar = GregorianCalendar(TimeZone.getTimeZone("GMT"))
    calendar.set(year, month - 1, day, hour, minute, second)
    calendar.set(Calendar.MILLISECOND, millis)
    return Date(calendar.timeInMillis - TimeUnit.MINUTES.toMillis(offset.toLong()))
  }
}