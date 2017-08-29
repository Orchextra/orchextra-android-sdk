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

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.util.Log
import com.gigigo.orchextra.core.domain.entities.Proximity
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.indoorpositioning.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconListener
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconScanner
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconScannerImp
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getBeaconManager
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getType
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getValue
import org.altbeacon.beacon.BeaconConsumer

class IndoorPositioningService : Service(), BeaconConsumer, BeaconListener {

  private lateinit var beaconScanner: BeaconScanner

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

    if (intent == null) {
      return START_NOT_STICKY
    }

    val config: List<Proximity> = intent.getParcelableArrayListExtra(CONFIG_EXTRA)
    beaconScanner = BeaconScannerImp(getBeaconManager(5000L), config, this, this)
    beaconScanner.start()

    return START_REDELIVER_INTENT
  }

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onDestroy() {
    super.onDestroy()
  }

  @RequiresApi(VERSION_CODES.JELLY_BEAN_MR2)
  override fun onBeaconServiceConnect() = beaconScanner.onBeaconServiceConnect()

  override fun onBeaconDetect(oxBeacon: OxBeacon) {
    sendOxBeaconEvent(oxBeacon)
  }

  private fun sendOxBeaconEvent(oxBeacon: OxBeacon) {

    // TODO get beacon event

    val trigger = Trigger(
        type = oxBeacon.getType(),
        value = oxBeacon.getValue(),
        event = "TODO",
        distance = oxBeacon.getDistanceQualifier(),
        temperature = oxBeacon.getTemperatureInCelsius(),
        battery = oxBeacon.batteryMilliVolts,
        uptime = oxBeacon.uptime)

    Log.d(TAG, "trigger: $trigger")
//    sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(trigger))
  }

  companion object Navigator {
    private val TAG = "IndoorPositioningS"
    private val CONFIG_EXTRA = "config_extra"
    private var intent: Intent? = null

    fun start(context: Context, config: ArrayList<Proximity>) {
      intent = Intent(context, IndoorPositioningService::class.java)
      intent?.putParcelableArrayListExtra(CONFIG_EXTRA, config)
      context.startService(intent)
    }

    fun stop(context: Context) {
      if (intent != null) {
        context.stopService(intent)
      }
    }
  }
}