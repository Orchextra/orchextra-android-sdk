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

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import com.gigigo.orchextra.core.domain.entities.Proximity
import com.gigigo.orchextra.core.domain.triggers.OxTrigger
import com.gigigo.orchextra.indoorpositioning.utils.extensions.getBeaconManager
import org.altbeacon.beacon.BeaconConsumer

class OxIndoorPositioningImp private constructor(private val context: Application) :
    OxTrigger<List<Proximity>>,
    BeaconConsumer {

  private lateinit var beaconScanner: BeaconScanner
  private lateinit var config: List<Proximity>

  override fun init() {
    beaconScanner = BeaconScannerImp(context.getBeaconManager(50000L), config, this)
    beaconScanner.start()
  }

  override fun setConfig(config: List<Proximity>) {
    this.config = config
  }

  override fun finish() {
    beaconScanner.stop()
  }

  override fun getApplicationContext(): Context = context

  override fun unbindService(conn: ServiceConnection) = context.unbindService(conn)

  override fun bindService(service: Intent, conn: ServiceConnection, flags: Int): Boolean =
      context.bindService(service, conn, flags)

  @RequiresApi(VERSION_CODES.JELLY_BEAN_MR2)
  override fun onBeaconServiceConnect() = beaconScanner.onBeaconServiceConnect()

  companion object Factory {

    private val TAG = "OxIndoorPositioningImp"

    fun create(context: Application): OxIndoorPositioningImp = OxIndoorPositioningImp(context)
  }
}