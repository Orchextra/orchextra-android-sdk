package com.gigigo.orchextra.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


/**
 * Receiver for geofence transition changes.
 *
 *
 * Receives geofence transition events from Location Services in the form of an Intent containing
 * the transition type and geofence id(s) that triggered the transition. Creates a JobIntentService
 * that will handle the intent in the background.
 */
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    /**
     * Receives incoming intents.
     *
     * @param context the application context.
     * @param intent  sent by Location Services. This Intent is provided to Location
     * Services (inside a PendingIntent) when addGeofences() is called.
     */
    override fun onReceive(context: Context, intent: Intent) {
        // Enqueues a JobIntentService passing the context and intent as parameters
        GeofenceTransitionsJobIntentService.enqueueWork(context, intent)
    }
}