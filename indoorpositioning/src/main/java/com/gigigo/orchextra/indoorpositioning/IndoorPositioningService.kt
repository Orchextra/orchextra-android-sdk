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

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.support.annotation.RequiresApi
import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.LogUtils.LOGW
import com.gigigo.orchextra.indoorpositioning.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconListener
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconScanner
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconScannerImp
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getBeaconManager
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getType
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getValue
import org.altbeacon.beacon.BeaconConsumer

class IndoorPositioningService : Service(), BeaconConsumer, BeaconListener {

  private val TAG = LogUtils.makeLogTag(IndoorPositioningService::class.java)
  private lateinit var alarmManager: AlarmManager
  private lateinit var beaconScanner: BeaconScanner
  private lateinit var config: List<IndoorPositionConfig>
  private var isRunning: Boolean = false

  private val SCAN_DELAY_IN_SECONDS = 30
  private val CHECK_SERVICE_TIME_IN_SECONDS = 60 * 5

  override fun onCreate() {
    super.onCreate()
    isRunning = false
    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

    if (intent == null) {
      return START_NOT_STICKY
    }

    if (!isRunning) {
      isRunning = true

      config = intent.getParcelableArrayListExtra(CONFIG_EXTRA)
      beaconScanner = BeaconScannerImp(getBeaconManager(SCAN_DELAY_IN_SECONDS * 1000L), config,
          this, this)
      beaconScanner.start()
    } else {
      LOGW(TAG, "IndoorPositioningService is already running")
    }

    setAlarmService()

    return START_STICKY
  }

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onDestroy() {
    beaconScanner.stop()

    super.onDestroy()
  }

  private fun setAlarmService() {
    val time = System.currentTimeMillis() + CHECK_SERVICE_TIME_IN_SECONDS * 1000
    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
  }

  @RequiresApi(VERSION_CODES.JELLY_BEAN_MR2)
  override fun onBeaconServiceConnect() = beaconScanner.onBeaconServiceConnect()

  override fun onBeaconsDetect(oxBeacons: List<OxBeacon>) {
    oxBeacons.forEach { sendOxBeaconEvent(it) }
  }

  private fun sendOxBeaconEvent(oxBeacon: OxBeacon) {
    val trigger = Trigger(
        type = oxBeacon.getType(),
        value = oxBeacon.getValue(),
        event = "enter",
        distance = oxBeacon.getDistanceQualifier(),
        temperature = oxBeacon.getTemperatureInCelsius(),
        battery = oxBeacon.batteryMilliVolts,
        uptime = oxBeacon.uptime)

    sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(trigger))
  }

  companion object Navigator {
    private val CONFIG_EXTRA = "config_extra"
    private var intent: Intent? = null
    private var pendingIntent: PendingIntent? = null

    fun start(context: Context, config: ArrayList<IndoorPositionConfig>) {
      pendingIntent = IndoorPositioningReceiver.getIndoorPositioningIntent(context, config)
      intent = Intent(context, IndoorPositioningService::class.java)
      intent?.putParcelableArrayListExtra(CONFIG_EXTRA, config)
      context.startService(intent)
    }

    fun stop(context: Context) {
      if (intent != null) {
        context.stopService(intent)
      }
      if (pendingIntent != null) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
      }
    }
  }
}