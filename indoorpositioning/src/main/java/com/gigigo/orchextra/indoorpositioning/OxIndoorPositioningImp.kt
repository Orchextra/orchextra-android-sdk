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

package com.gigigo.orchextra.indoorpositioning

import android.app.Application
import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.core.domain.triggers.OxTrigger
import com.gigigo.orchextra.indoorpositioning.domain.datasource.IPDbDataSource

class OxIndoorPositioningImp private constructor(private val context: Application,
    private val dataSource: IPDbDataSource) : OxTrigger<List<IndoorPositionConfig>> {

  private lateinit var config: List<IndoorPositionConfig>

  override fun init() {
    if (config.isNotEmpty()) {
      IndoorPositioningService.start(context)
    }
  }

  override fun setConfig(config: List<IndoorPositionConfig>) {
    this.config = config
    dataSource.saveConfig(config)
  }

  override fun finish() {
    IndoorPositioningService.stop(context)
  }

  companion object Factory {
    fun create(context: Application): OxIndoorPositioningImp =
        OxIndoorPositioningImp(context, IPDbDataSource.create(context))
  }
}