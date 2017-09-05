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

package com.gigigo.orchextra.geofence

import android.app.IntentService
import android.content.Intent
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.GEOFENCE
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.LogUtils.LOGE
import com.gigigo.orchextra.geofence.R.string
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsIntentService : IntentService(TAG) {

  override fun onHandleIntent(intent: Intent?) {

    val geofencingEvent = GeofencingEvent.fromIntent(intent)
    if (geofencingEvent.hasError()) {
      val errorMessage = GeofenceErrorMessages.getErrorString(this, geofencingEvent.errorCode)
      LOGE(TAG, errorMessage)
      return
    }

    val geofenceTransition = geofencingEvent.geofenceTransition

    if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
      val triggeringGeofences = geofencingEvent.triggeringGeofences

      val trigger = Trigger(
          type = GEOFENCE,
          value = triggeringGeofences[0].requestId,
          event = getTransitionString(geofenceTransition))

      sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(trigger))
    } else {
      LOGE(TAG, getString(string.geofence_transition_invalid_type, geofenceTransition))
    }
  }

  private fun getTransitionString(transitionType: Int): String = when (transitionType) {
    Geofence.GEOFENCE_TRANSITION_ENTER -> getString(R.string.geofence_transition_entered)
    Geofence.GEOFENCE_TRANSITION_EXIT -> getString(R.string.geofence_transition_exited)
    Geofence.GEOFENCE_TRANSITION_DWELL -> getString(R.string.geofence_transition_stay)
    else -> getString(R.string.unknown_geofence_transition)
  }

  companion object {
    private val TAG = LogUtils.makeLogTag(GeofenceTransitionsIntentService::class.java)
  }
}