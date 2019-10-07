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

import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import com.gigigo.orchextra.core.domain.entities.GeoMarketing
import com.gigigo.orchextra.core.domain.triggers.OxTrigger
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.LogUtils.LOGD
import com.gigigo.orchextra.core.utils.LogUtils.LOGE
import com.gigigo.orchextra.geofence.utils.toGeofence
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class OxGeofenceImp private constructor(
    private val context: Application,
    private val geofencingClient: GeofencingClient
) : OxTrigger<List<GeoMarketing>>, OnCompleteListener<Void> {

    private var geofenceList: List<Geofence> = ArrayList()
    private var geofencePendingIntent: PendingIntent? = null

    override fun init() {
        if (geofenceList.isEmpty()) {
            LOGD(TAG, "geofences list is empty")
            return
        }
        connectWithCallbacks()
        addGeofences()
    }

    override fun setConfig(config: List<GeoMarketing>) {
        geofenceList = config.map { it.toGeofence() }
    }

    override fun finish() {
        removeGeofences()
    }

    private fun addGeofences() {
        if (
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnCompleteListener(this)
        } else {
            throw SecurityException("Geofence trigger needs ACCESS_FINE_LOCATION permission")
        }
    }

    private fun removeGeofences() {
        geofencingClient.removeGeofences(getGeofencePendingIntent()).addOnCompleteListener(this)
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent as PendingIntent
        }

        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        geofencePendingIntent = PendingIntent.getBroadcast(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return geofencePendingIntent as PendingIntent
    }

    private fun connectWithCallbacks() {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(connectionAddListener)
            .addOnConnectionFailedListener(connectionFailedListener)
            .build()
        googleApiClient.connect()
    }

    private val connectionAddListener = object : GoogleApiClient.ConnectionCallbacks {
        override fun onConnected(bundle: Bundle?) {
            LOGD(TAG, "connectionAddListener")
        }

        override fun onConnectionSuspended(i: Int) {
            LOGD(TAG, "onConnectionSuspended")
        }
    }

    private val connectionFailedListener = GoogleApiClient.OnConnectionFailedListener {
        LOGE(TAG, "connectionFailedListener")
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful) {
            updateGeofencesAdded(!getGeofencesAdded())

            val message =
                if (getGeofencesAdded()) context.getString(R.string.geofences_added)
                else context.getString(R.string.geofences_removed)
            LOGD(TAG, "onComplete: $message")
        } else {
            task.exception?.let {
                LOGE(TAG, it.message ?: "no exception message")
                val errorMessage = GeofenceErrorMessages.getErrorString(context, it)
                LOGE(TAG, "onComplete: $errorMessage")
            }
        }
    }

    private fun getGeofencingRequest(): GeofencingRequest {
        val builder = GeofencingRequest.Builder()
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        builder.addGeofences(geofenceList)
        return builder.build()
    }

    private fun getGeofencesAdded(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(GEOFENCES_ADDED_KEY, false)
    }

    private fun updateGeofencesAdded(added: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(GEOFENCES_ADDED_KEY, added)
            .apply()
    }

    companion object Factory {
        private val TAG = LogUtils.makeLogTag(OxGeofenceImp::class.java)
        private const val GEOFENCES_ADDED_KEY = "GEOFENCES_ADDED_KEY"

        fun create(context: Application) =
            OxGeofenceImp(context, LocationServices.getGeofencingClient(context))
    }
}