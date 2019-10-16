package com.gigigo.orchextra.core.domain.location

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.location.Location
import android.os.IBinder
import android.preference.PreferenceManager
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gigigo.orchextra.core.domain.entities.OxPoint

private val TAG = OxLocationUpdates::class.java.simpleName

class OxLocationUpdates {

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private var receiver: LocationReceiver? = null

    // A reference to the service used to get location updates.
    private var service: LocationUpdatesService? = null

    // Tracks the bound state of the service.
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, connected: IBinder) {
            val binder = connected as LocationUpdatesService.LocalBinder
            service = binder.service
            isBound = true
            listener?.onLocationConnected()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
            isBound = false
            listener?.onLocationDisconnected()
        }
    }

    var listener: OxLocationListener? = null

    fun startLocationUpdates() {
        if (service == null) Log.e(TAG, "Location Service is not connected")
        if (!isBound) Log.e(TAG, "Location Service is not bound")
        service?.requestLocationUpdates()
    }

    fun stopLocationUpdates() {
        service?.removeLocationUpdates()
    }

    fun onCreate() {
        receiver = LocationReceiver()
    }

    fun onStart(context: Context) {
        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        context.bindService(
            Intent(context, LocationUpdatesService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun onResume(context: Context) {
        val receiver = this.receiver ?: return
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(
                receiver,
                IntentFilter(LocationUpdatesService.ACTION_BROADCAST)
            )
    }

    fun onPause(context: Context) {
        val receiver = this.receiver ?: return
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
    }

    fun onStop(context: Context) {
        if (isBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            context.unbindService(serviceConnection)
            isBound = false
        }
    }

    private inner class LocationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val location =
                intent.getParcelableExtra<Location>(LocationUpdatesService.EXTRA_LOCATION)
            if (location != null) {
                listener?.onLocationUpdated(OxPoint(location.latitude, location.longitude))
            }
        }
    }

    companion object {

        var LOCATION_LAT = "LOCATION_LAT"
        var LOCATION_LON = "LOCATION_LON"
        var LOCATION_PROVIDER = "LOCATION_PROVIDER"

        fun saveLastLocation(context: Context, location: Location) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPreferences.edit().putString(LOCATION_LAT, location.latitude.toString()).apply()
            sharedPreferences.edit().putString(LOCATION_LON, location.longitude.toString()).apply()
            sharedPreferences.edit().putString(LOCATION_PROVIDER, location.provider).apply()
        }

        fun getLastLocationSaved(context: Context): Location {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

            val location = Location(sharedPreferences.getString(LOCATION_PROVIDER, ""))
            location.latitude = sharedPreferences.getString(LOCATION_LAT, "")?.toDouble() ?: 0.0
            location.longitude = sharedPreferences.getString(LOCATION_LON, "")?.toDouble() ?: 0.0

            return location
        }
    }
}

interface OxLocationListener {
    fun onLocationUpdated(location: OxPoint)
    fun onLocationConnected()
    fun onLocationDisconnected()
}