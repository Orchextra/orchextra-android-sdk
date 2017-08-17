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

package com.gigigo.orchextra.core.data.datasources.db.caching.strategy.ttl

import com.gigigo.orchextra.core.data.datasources.db.caching.strategy.CachingStrategy
import java.util.concurrent.TimeUnit


class TtlCachingStrategy<T : TtlCachingObject>(ttl: Int, timeUnit: TimeUnit) : CachingStrategy<T> {

  private val ttlMillis: Long = timeUnit.toMillis(ttl.toLong())

  override fun isValid(data: T): Boolean =
      data.getPersistedTime() + ttlMillis > System.currentTimeMillis()

}