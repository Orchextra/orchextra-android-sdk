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

package com.gigigo.orchextra.indoorpositioning.scanner

import android.os.Build.VERSION_CODES
import android.os.RemoteException
import androidx.annotation.RequiresApi
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.LogUtils.LOGD
import com.gigigo.orchextra.core.utils.LogUtils.LOGE
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.utils.extensions.toOxBeacon
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.Region

class BeaconScannerImp(private val beaconManager: BeaconManager,
    private val consumer: BeaconConsumer) : BeaconScanner {

  private var listener: (OxBeacon) -> Unit = {}
  private var finish: () -> Unit = {}

  override fun start(listener: (OxBeacon) -> Unit, finish: () -> Unit) {
    this.listener = listener
    this.finish = finish
    startScan()
  }

  override fun stop() {
    stopScan()
  }

  @RequiresApi(VERSION_CODES.JELLY_BEAN_MR2)
  override fun onBeaconServiceConnect() {
    LOGD(TAG, "beaconManager is bound, ready to start scanning")

    beaconManager.addRangeNotifier { beacons, _ ->
      beacons.forEach { listener(it.toOxBeacon()) }
      finish()
    }

    try {
      beaconManager.startRangingBeaconsInRegion(
          Region("com.gigigo.orchextra.beaconscanner", null, null, null))
    } catch (e: RemoteException) {
      LOGE(TAG, "onBeaconServiceConnect()", e)
    }
  }

  private fun startScan() {
    LOGD(TAG, "binding beaconManager")
    beaconManager.bind(consumer)
  }

  private fun stopScan() {
    LOGD(TAG, "Unbinding from beaconManager")
    beaconManager.unbind(consumer)
  }

  companion object {
    private val TAG = LogUtils.makeLogTag(BeaconScannerImp::class.java)
  }
}