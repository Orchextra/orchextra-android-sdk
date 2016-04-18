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

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.bluetooth.BluetoothAvailabilityImpl;
import com.gigigo.orchextra.device.bluetooth.BluetoothStatusInfoImpl;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothAvailability;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusInfo;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Singleton;
import org.altbeacon.beacon.BeaconManager;


@Module(includes = BeaconsModule.class)
public class BluetoothModule {

  @Provides @Singleton
  BluetoothAvailability provideBluetoothAvailability(BeaconManager beaconManager){
    return new BluetoothAvailabilityImpl(beaconManager);
  }

  @Provides @Singleton BluetoothStatusInfo provideBluetoothStatusInfo(BluetoothAvailability bluetoothAvailability,
      PermissionChecker permissionChecker, ContextProvider contextProvider, AppRunningMode appRunningMode,
      FeatureListener featureListener){
    return new BluetoothStatusInfoImpl(permissionChecker, bluetoothAvailability, contextProvider,
        appRunningMode, featureListener);
  }

}
