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

package com.gigigo.orchextra.core.actions

import com.gigigo.orchextra.core.actions.actionexecutors.BrowserActionExecutor
import com.gigigo.orchextra.core.actions.actionexecutors.WebViewActionExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.BROWSER
import com.gigigo.orchextra.core.domain.entities.ActionType.CUSTOM_SCHEME
import com.gigigo.orchextra.core.domain.entities.ActionType.IMAGE_RECOGNITION
import com.gigigo.orchextra.core.domain.entities.ActionType.NOTHING
import com.gigigo.orchextra.core.domain.entities.ActionType.NOTIFICATION
import com.gigigo.orchextra.core.domain.entities.ActionType.SCANNER
import com.gigigo.orchextra.core.domain.entities.ActionType.WEBVIEW

class ActionDispatcher constructor(private val browserActionExecutor: BrowserActionExecutor,
    private val webViewActionExecutor: WebViewActionExecutor) {

  fun executeAction(action: Action) {

    when (action.type) {
      BROWSER -> browserActionExecutor.open(action.url)
      WEBVIEW -> webViewActionExecutor.open(action.url)
      CUSTOM_SCHEME -> print("CUSTOM_SCHEME")
      SCANNER -> print("SCANNER")
      IMAGE_RECOGNITION -> print("IMAGE_RECOGNITION")
      NOTIFICATION -> print("NOTIFICATION")
      NOTHING -> print("NOTHING")
    }
  }

  companion object Factory {

    fun create(): ActionDispatcher = ActionDispatcher(
        BrowserActionExecutor.create(),
        WebViewActionExecutor.create())
  }
}