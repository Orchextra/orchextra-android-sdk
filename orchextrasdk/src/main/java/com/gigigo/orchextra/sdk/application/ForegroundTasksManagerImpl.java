/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
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

package com.gigigo.orchextra.sdk.application;

import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;


public class ForegroundTasksManagerImpl implements ForegroundTasksManager {

  private final BeaconScanner beaconScanner;
  private final ConfigDelegateImp configDelegateImp;
  private final GeofenceRegister geofenceRegister;

  public ForegroundTasksManagerImpl(BeaconScanner beaconScanner,
                                    ConfigDelegateImp configDelegateImp,
                                    GeofenceRegister geofenceRegister) {
    this.beaconScanner = beaconScanner;
    this.configDelegateImp = configDelegateImp;
    this.geofenceRegister = geofenceRegister;
  }

  @Override public void startForegroundTasks() {
    beaconScanner.startMonitoring();
    beaconScanner.initAvailableRegionsRangingScanner();

    geofenceRegister.startGeofenceRegister();

    configDelegateImp.sendConfiguration();
  }

  @Override public void finalizeForegroundTasks() {
    beaconScanner.stopRangingScanner();
  }
}
