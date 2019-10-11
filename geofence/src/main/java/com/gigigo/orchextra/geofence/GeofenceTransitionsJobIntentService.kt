package com.gigigo.orchextra.geofence

import android.content.Context
import android.content.Intent
import android.location.Location
import android.preference.PreferenceManager
import androidx.core.app.JobIntentService
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType
import com.gigigo.orchextra.core.domain.location.OxLocationUpdates
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

    val GEOFENCE_REQUEST_ID: String = "GEOFENCE_REQUEST_ID"

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

//        checkGeofenceByRadius(geofenceTransition, geofencingEvent)

        if (geofenceTransition == GEOFENCE_TRANSITION_ENTER || geofenceTransition == GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            triggeringGeofences.firstOrNull()?.requestId?.let { requestId ->

                if (geofenceTransition == GEOFENCE_TRANSITION_ENTER) {
                    if (!isSent(geofenceTransition, requestId)) {
                        LOGE(TAG, "Geofence $requestId saved, showing notification EXIT")
                        saveRequestId(geofenceTransition, requestId)
                        deleteTransitionExit(requestId)
                        sendTriggerBroadcast(transition, requestId)
                    } else {
                        LOGE(TAG, "Geofence $requestId already saved ENTER")
                    }
                }else if (geofenceTransition == GEOFENCE_TRANSITION_EXIT){
                    if (!isSent(geofenceTransition, requestId)) {
                        LOGE(TAG, "Geofence $requestId saved, showing notification EXIT")
                        saveRequestId(geofenceTransition, requestId)
                        deleteTransitionEnter(requestId)
                        sendTriggerBroadcast(transition, requestId)
                    } else {
                        LOGE(TAG, "Geofence $requestId already saved EXIT")
                    }
                }
            }
        } else {
            // Log the error.
            LOGE(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition))
        }
    }

    private fun checkGeofenceByRadius(
        geofenceTransition: Int,
        geofencingEvent: GeofencingEvent
    ) {
        val lastLocationSaved = OxLocationUpdates.getLastLocationSaved(applicationContext)
        val transitionLocation = geofencingEvent.triggeringLocation

        geofencingEvent.triggeringGeofences.firstOrNull()?.requestId?.let { requestId ->

            val radius = OxGeofenceImp.getGeofenceRadius(applicationContext, requestId)
            when (geofenceTransition) {
                GEOFENCE_TRANSITION_ENTER -> {

                    val geofenceLocation = insideGeofenceLocation(lastLocationSaved, transitionLocation, radius.toFloat())
                    if (geofenceLocation != null) {
                        LOGD(TAG, "OX Geofence in!!")
                    }
                }
                GEOFENCE_TRANSITION_EXIT -> {
                    val geofenceLocation = insideGeofenceLocation(lastLocationSaved, transitionLocation, radius.toFloat())
                    if (geofenceLocation == null) {
                        LOGD(TAG, "OX Geofence Out!!")
                    }
                }
                else -> // Log the error.
                    LOGE(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition))
            }
        }
    }

    private fun deleteTransitionExit(requestId: String) {
        val key = "$GEOFENCE_REQUEST_ID-$requestId-$GEOFENCE_TRANSITION_EXIT"
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().remove(key).apply()
    }

    private fun deleteTransitionEnter(requestId: String) {
        val key = "$GEOFENCE_REQUEST_ID-$requestId-$GEOFENCE_TRANSITION_ENTER"
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().remove(key).apply()
    }

    fun insideGeofenceLocation(
        currentLocation: Location,
        listOfGeofenceLocations: ArrayList<Location>,
        triggerRadius: Float
    ): Location? {
        for (geoFenceLocation in listOfGeofenceLocations) {
            if (currentLocation.distanceTo(geoFenceLocation) < triggerRadius)
                return geoFenceLocation
        }
        return null
    }

    fun insideGeofenceLocation(
        currentLocation: Location,
        geofenceLocation: Location,
        triggerRadius: Float
    ): Location? {
        if (currentLocation.distanceTo(geofenceLocation) < triggerRadius)
            return geofenceLocation

        return null
    }

    /**
     * Save requestId on preferences.
     */
    private fun saveRequestId(geofenceTransition: Int, requestId: String) {
        val key = "$GEOFENCE_REQUEST_ID-$requestId-$geofenceTransition"

        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            .putString(key, key).apply()
    }

    /**
     * Check if requestId was sent
     */
    private fun isSent(geofenceTransition: Int, requestId: String): Boolean {
        val key = "$GEOFENCE_REQUEST_ID-$requestId-$geofenceTransition"
        val value = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(key, null)

        return value.equals(key)
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
            enqueueWork(
                context, GeofenceTransitionsJobIntentService::class.java,
                JOB_ID, intent
            )
        }
    }
}