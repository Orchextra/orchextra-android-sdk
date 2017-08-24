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

package com.gigigo.orchextra.core.domain.actions.actionexecutors.browser

import android.content.Context
import android.content.Intent
import android.net.Uri

class BrowserActionExecutor(private val context: Context) {

  fun open(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(browserIntent)
  }

  companion object Factory {

    fun create(context: Context): BrowserActionExecutor = BrowserActionExecutor(context)
  }
}