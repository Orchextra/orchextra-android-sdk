package com.gigigo.orchextrasdk.demo.ui.geofences

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.gigigo.orchextra.core.domain.location.LocationUpdatesService
import com.gigigo.orchextra.core.domain.location.OxLocationUpdates
import com.gigigo.orchextra.geofence.GeofenceTransitionsJobIntentService



class BootCompleteIntentReceiver : BroadcastReceiver() {
    private val TAG: String? = BootCompleteIntentReceiver::class.java.simpleName
    private lateinit var locationManager: OxLocationUpdates



    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive:")

         val serviceConnection = object : ServiceConnection {
            private lateinit var service: LocationUpdatesService

            override fun onServiceConnected(name: ComponentName, connected: IBinder) {
                Log.d(TAG, "onServiceConnected")

                val binder = connected as LocationUpdatesService.LocalBinder
                service = binder.service

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context?.applicationContext?.startForegroundService(Intent(context, LocationUpdatesService::class.java))
                } else {
                    context?.applicationContext?.startService(Intent(context, LocationUpdatesService::class.java))
                }
                // This is the key: Without waiting Android Framework to call this method
                // inside Service.onCreate(), immediately call here to post the notification.
                service.startForeground(LocationUpdatesService.NOTIFICATION_ID, service.notification)

                // Release the connection to prevent leaks.
                context?.unbindService(this)
                service.requestLocationUpdates(false)
            }

            override fun onServiceDisconnected(name: ComponentName) {
            }
        }


        if (intent != null) {
            if ("android.intent.action.BOOT_COMPLETED" == intent.action) {

                Toast.makeText(context, "android.intent.action.BOOT_COMPLETED", Toast.LENGTH_LONG)
                    .show()

                context?.applicationContext?.bindService(
                    Intent(context, LocationUpdatesService::class.java),
                    serviceConnection,
                    Context.BIND_AUTO_CREATE
                )

                Log.d(TAG, "android.intent.action.BOOT_COMPLETED: LocationUpdatesService")

                // Enqueues a JobIntentService passing the context and intent as parameters
                context?.let {
                    GeofenceTransitionsJobIntentService.enqueueWork(it, intent)
                }
            }
        }
    }


}