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

package com.gigigo.orchextra.domain.background;

import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;


public class BackgroundTasksManagerImpl implements BackgroundTasksManager {

  private final BeaconScanner beaconScanner;
  private final GeofenceRegister geofenceRegister;

  public BackgroundTasksManagerImpl(BeaconScanner beaconScanner,
      GeofenceRegister geofenceRegister) {
    this.beaconScanner = beaconScanner;
    this.geofenceRegister = geofenceRegister;
  }

  @Override public void startBackgroundTasks() {
    beaconScanner.startMonitoring();
    geofenceRegister.startGeofenceRegister();
  }

  @Override public void finalizeBackgroundTasks() {
    //beaconScanner.stopMonitoring();
  }
}
