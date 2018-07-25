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
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Handler
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.gigigo.orchextra.core.R.string
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON_REGION
import com.gigigo.orchextra.core.domain.entities.TriggerType.EDDYSTONE_REGION
import com.gigigo.orchextra.core.domain.interactor.ValidateTrigger
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.LogUtils.LOGD
import com.gigigo.orchextra.core.utils.LogUtils.LOGE
import com.gigigo.orchextra.core.utils.LogUtils.LOGW
import com.gigigo.orchextra.indoorpositioning.domain.IndoorPositioningValidator
import com.gigigo.orchextra.indoorpositioning.domain.RegionsDetector
import com.gigigo.orchextra.indoorpositioning.domain.datasource.IPDbDataSource
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeaconRegion
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconScanner
import com.gigigo.orchextra.indoorpositioning.scanner.BeaconScannerImp
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getBeaconManager
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getType
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getValue
import org.altbeacon.beacon.BeaconConsumer

class IndoorPositioningService : Service(), BeaconConsumer {

  private lateinit var alarmManager: AlarmManager
  private lateinit var beaconScanner: BeaconScanner
  private lateinit var regionsDetector: RegionsDetector
  private lateinit var validator: IndoorPositioningValidator
  private lateinit var validateTrigger: ValidateTrigger
  private lateinit var dataSource: IPDbDataSource
  private var isRunning: Boolean = false
  private var timerStarted = false
  private val handler = Handler()

  override fun onCreate() {
    super.onCreate()
    this.isRunning = false
    this.alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    dataSource = IPDbDataSource.create(this)

    showNotification()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

    if (!isRunning) {
      isRunning = true

      val dbDataSource = DbDataSource.create(this)
      val config = dataSource.getConfig()

      validator = IndoorPositioningValidator(config)
      validateTrigger = ValidateTrigger.create(DbDataSource.create(applicationContext))
      beaconScanner = BeaconScannerImp(getBeaconManager(dbDataSource.getScanTime()), this)
      regionsDetector = RegionsDetector.create(config, this) { sendOxBeaconRegionEvent(it) }
      beaconScanner.start({
        regionsDetector.onBeaconDetect(it)
        sendOxBeaconEvent(it)
      }, {
        stopSelf()
      })
    } else {
      LOGW(TAG, "IndoorPositioningService is already running")
    }

    setAlarmService()
    startTimer()

    return START_NOT_STICKY
  }

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onDestroy() {
    LOGD(TAG, "onDestroy")
    beaconScanner.stop()
    stopTimer()
    super.onDestroy()

    val notificationManager = applicationContext.getSystemService(
        Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(NOTIFICATION_ID)
  }

  private fun setAlarmService() {
    LOGD(TAG, "setAlarmService()")
    val time = System.currentTimeMillis() + CHECK_SERVICE_TIME_IN_SECONDS * 1000
    alarmManager.set(AlarmManager.RTC_WAKEUP, time,
        IndoorPositioningReceiver.getIndoorPositioningIntent(this))
  }

  @RequiresApi(VERSION_CODES.JELLY_BEAN_MR2)
  override fun onBeaconServiceConnect() = beaconScanner.onBeaconServiceConnect()

  private fun sendOxBeaconEvent(beacon: OxBeacon) {

    if (validator.isValid(beacon)) {
      val trigger = Trigger(
          type = beacon.getType(),
          value = beacon.getValue(),
          distance = beacon.getDistanceQualifier(),
          temperature = beacon.getTemperatureInCelsius(),
          battery = beacon.batteryMilliVolts,
          uptime = beacon.uptime)

      if (validateTrigger.isValid(trigger)) {
        sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(this, trigger))
      }
    } else {
      LOGD(TAG, "Invalid beacon: $beacon")
    }
  }

  private fun sendOxBeaconRegionEvent(beaconRegion: OxBeaconRegion) {

    if (validator.isValid(beaconRegion)) {
      val type = if (beaconRegion.beacon.getType() == BEACON) {
        BEACON_REGION
      } else {
        EDDYSTONE_REGION
      }

      val trigger = Trigger(
          type = type,
          value = beaconRegion.value,
          event = beaconRegion.event)

      if (validateTrigger.isValid(trigger)) {
        sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(this, trigger))
      }
    } else {
      LOGD(TAG, "Invalid region: $beaconRegion")
    }
  }

  private val runnable = Runnable {
    regionsDetector.checkForExitEvents()
    if (timerStarted) {
      startTimer()
    }
  }

  private fun stopTimer() {
    timerStarted = false
    handler.removeCallbacks(runnable)
  }

  private fun startTimer() {
    timerStarted = true
    handler.postDelayed(runnable, 30 * 1000)
  }


  private fun showNotification() {

    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (VERSION.SDK_INT >= VERSION_CODES.O) {
      val chan1 = NotificationChannel(PRIMARY_CHANNEL, getString(string.app_name),
          NotificationManager.IMPORTANCE_DEFAULT)
      chan1.lightColor = Color.RED
      chan1.lockscreenVisibility = android.app.Notification.VISIBILITY_PRIVATE
      manager.createNotificationChannel(chan1)

//      val handler = Handler()
//      handler.postDelayed({
      val mBuilder = NotificationCompat.Builder(this, PRIMARY_CHANNEL)
          .setSmallIcon(R.drawable.ox_notification_large_icon)
          .setContentTitle(getString(R.string.app_name))
          .setPriority(NotificationCompat.PRIORITY_DEFAULT)
          .setVibrate(longArrayOf(0, 0, 0))
          .setLights(Color.RED, 0, 100)
          .setSound(null)

      startForeground(NOTIFICATION_ID, mBuilder.build())
//      }, 4000)
    }
  }

  companion object Navigator {
    private val TAG = LogUtils.makeLogTag(IndoorPositioningService::class.java)
    private const val CHECK_SERVICE_TIME_IN_SECONDS = 30
    private const val PRIMARY_CHANNEL = "default"
    private const val NOTIFICATION_ID = 0x654

    fun start(context: Context) {
      val intent = Intent(context, IndoorPositioningService::class.java)
      try {
        context.startService(intent)
      } catch (exception: IllegalStateException) {
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
          context.startForegroundService(intent)
        }
      }
    }

    fun stop(context: Context) {
      LOGE(TAG, "Stop indoorPositioningService")
    }
  }
}