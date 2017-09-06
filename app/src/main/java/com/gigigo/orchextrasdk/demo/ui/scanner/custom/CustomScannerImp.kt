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

package com.gigigo.orchextrasdk.demo.ui.scanner.custom

import android.app.Application
import com.gigigo.orchextra.core.domain.triggers.OxTrigger

class CustomScannerImp private constructor(private val context: Application) : OxTrigger<Any> {

  override fun init() {
    CustomScannerActivity.open(context)
  }

  override fun setConfig(config: Any) {
  }

  override fun finish() {
    CustomScannerActivity.finish()
  }

  companion object Factory {

    fun create(context: Application): CustomScannerImp = CustomScannerImp(context)
  }
}