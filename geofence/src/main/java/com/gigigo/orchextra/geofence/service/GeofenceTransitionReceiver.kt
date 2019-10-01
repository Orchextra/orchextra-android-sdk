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

package com.gigigo.orchextra.geofence.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GeofenceTransitionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        GeofenceTransitionsService.start(context)
    }

    companion object {
        const val GEOFENCE_TRANSITION_RC = 0x532
        fun getGeofenceTransitionIntent(context: Context): PendingIntent {
            val alarmIntent = Intent(context, GeofenceTransitionReceiver::class.java)
            return PendingIntent.getBroadcast(
                context, GEOFENCE_TRANSITION_RC, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }
}