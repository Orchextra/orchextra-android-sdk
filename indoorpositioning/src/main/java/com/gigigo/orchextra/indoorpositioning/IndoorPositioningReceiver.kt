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

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class IndoorPositioningReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        IndoorPositioningService.start(context)
    }

    companion object {

        fun getIndoorPositioningIntent(context: Context): PendingIntent {
            val alarmIntent = Intent(context, IndoorPositioningReceiver::class.java)
            return PendingIntent.getBroadcast(
                context, 0x432, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }
}