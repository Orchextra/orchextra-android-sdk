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

package com.gigigo.orchextra.core.receiver

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.triggers.TriggerListener
import com.gigigo.orchextra.core.domain.triggers.TriggerManager

class TriggerHandlerService : IntentService(TAG) {

  private val triggerListener: TriggerListener = TriggerManager.create()

  override fun onHandleIntent(intent: Intent) {
    val trigger = intent.getParcelableExtra<Trigger>(TRIGGER_EXTRA)
    triggerListener.onTriggerDetected(trigger)
  }

  companion object Navigator {
    private val TAG = "TriggerHandlerService"
    val TRIGGER_EXTRA = "trigger_extra"

    fun start(context: Context, trigger: Trigger) {
      val intent = Intent(context, TriggerHandlerService::class.java)
      intent.putExtra(TRIGGER_EXTRA, trigger)
      context.startService(intent)
    }
  }
}