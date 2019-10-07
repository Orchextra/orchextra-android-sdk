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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gigigo.orchextra.core.Orchextra
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.triggers.TriggerHandlerService
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.LogUtils.LOGD
import com.gigigo.orchextra.core.utils.LogUtils.LOGE

class TriggerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val trigger = intent.getParcelableExtra<Trigger>(TRIGGER_EXTRA)?.copy(
            phoneStatus = getStatus()
        )
        if (trigger == null) {
            LOGE(TAG, "trigger is null")
            return
        }
        LOGD(TAG, "onTriggerReceived: $trigger")
        TriggerHandlerService.start(context, trigger)
    }

    companion object Navigator {
        private val TAG = LogUtils.makeLogTag(TriggerBroadcastReceiver::class.java)
        private const val TRIGGER_RECEIVER = "com.gigigo.orchextra.TRIGGER_RECEIVER"
        private const val TRIGGER_EXTRA = "trigger_extra"

        fun getTriggerIntent(context: Context, trigger: Trigger): Intent {
            val intent = Intent(TRIGGER_RECEIVER)
            intent.putExtra(TRIGGER_EXTRA, trigger)
            intent.setClass(context, TriggerBroadcastReceiver::class.java)
            return intent
        }
    }

    private fun getStatus(): String =
        if (Orchextra.isActivityRunning()) "foreground"
        else "background"
}