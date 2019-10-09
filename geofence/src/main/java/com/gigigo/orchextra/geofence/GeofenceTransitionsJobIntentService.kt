package com.gigigo.orchextra.geofence

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.orchextra.core.utils.LogUtils.LOGD
import com.gigigo.orchextra.core.utils.LogUtils.LOGE
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.GeofencingEvent


/**
 * Listener for geofence transition changes.
 *
 * Receives geofence transition events from Location Services in the form of an Intent containing
 * the transition type and geofence id(s) that triggered the transition.
 */
class GeofenceTransitionsJobIntentService : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        LOGD(TAG, "onCreate")
    }
    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     * Services (inside a PendingIntent) when addGeofences() is called.
     */
    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorString(
                this,
                geofencingEvent.errorCode
            )
            LOGE(TAG, errorMessage)
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition
        val transition = getTransitionString(geofenceTransition)
        LOGD(TAG, "OX Geofence triggered $transition")

        if (geofenceTransition == GEOFENCE_TRANSITION_ENTER ||
            geofenceTransition == GEOFENCE_TRANSITION_EXIT
        ) {
            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            triggeringGeofences.firstOrNull()?.requestId?.let { requestId ->
                sendTriggerBroadcast(transition, requestId)
            }
        } else {
            // Log the error.
            LOGE(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition))
        }
    }

    private fun sendTriggerBroadcast(transition: String, requestId: String) {
        LOGD(TAG, "OX sending Trigger broadcast")
        val trigger = Trigger(
            type = TriggerType.GEOFENCE,
            value = requestId,
            event = transition
        )
        sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(this, trigger))
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in Geofence
     * @return                  A String indicating the type of transition
     */
    private fun getTransitionString(transitionType: Int): String {
        return when (transitionType) {
            GEOFENCE_TRANSITION_ENTER -> getString(R.string.geofence_transition_entered)
            GEOFENCE_TRANSITION_EXIT -> getString(R.string.geofence_transition_exited)
            Geofence.GEOFENCE_TRANSITION_DWELL -> getString(R.string.geofence_transition_stay)
            else -> getString(R.string.unknown_geofence_transition)
        }
    }

    companion object {
        private const val JOB_ID = 573
        private const val TAG = "GeofenceTransitionsIS"

        /**
         * Convenience method for enqueuing work in to this service.
         */
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, GeofenceTransitionsJobIntentService::class.java,
                JOB_ID, intent)
        }
    }
}