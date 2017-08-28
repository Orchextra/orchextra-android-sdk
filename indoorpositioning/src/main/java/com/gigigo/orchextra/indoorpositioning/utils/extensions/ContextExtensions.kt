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

package com.gigigo.orchextra.indoorpositioning.utils.extensions

import android.content.Context
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser

fun Context.getBeaconManager(scanDelay: Long): BeaconManager = with(this) {

  val instance = BeaconManager.getInstanceForApplication(this)

  instance.foregroundBetweenScanPeriod = scanDelay

  instance.beaconParsers.add(
      BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"))
  instance.beaconParsers.add(BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT))
  instance.beaconParsers.add(BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT))
  instance.beaconParsers.add(BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT))

  return instance
}