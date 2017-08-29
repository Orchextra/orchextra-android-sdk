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

import android.os.Build.VERSION_CODES
import android.os.RemoteException
import android.support.annotation.RequiresApi
import android.util.Log
import com.gigigo.orchextra.core.domain.entities.Proximity
import com.gigigo.orchextra.indoorpositioning.utils.extensions.isInRegion
import com.gigigo.orchextra.indoorpositioning.utils.extensions.toOxBeacon
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.Region

class BeaconScannerImp(private val beaconManager: BeaconManager,
    private val config: List<Proximity>,
    private val consumer: BeaconConsumer) : BeaconScanner {

  override fun start() {
    startScan()
  }

  override fun stop() {
    stopScan()
  }

  @RequiresApi(VERSION_CODES.JELLY_BEAN_MR2)
  override fun onBeaconServiceConnect() {
    Log.d(TAG, "beaconManager is bound, ready to start scanning")

    beaconManager.addRangeNotifier { beacons, region ->

      Log.d(TAG, "--------> Beacons!!!!${beacons.map { it.toOxBeacon().toString() }}")
      Log.d(TAG, "--------> Regions!!!!$region")

      val filteredBeacons = beacons.map { it.toOxBeacon() }.filter { it.isInRegion(config) }

      Log.d(TAG, "--------> Filtered!!!! $filteredBeacons")
    }

    try {
      beaconManager.startRangingBeaconsInRegion(
          Region("com.gigigo.orchextra.beaconscanner", null, null, null))
    } catch (e: RemoteException) {
      e.printStackTrace()
    }
  }

  fun startScan() {
    Log.d(TAG, "binding beaconManager")
    beaconManager.bind(consumer)
  }

  fun stopScan() {
    Log.d(TAG, "Unbinding from beaconManager")
    beaconManager.unbind(consumer)
  }

  companion object {
    private val TAG = "BeaconScannerImp"
  }
}