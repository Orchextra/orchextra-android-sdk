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
import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.beacons.BeaconsController;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.device.bluetooth.beacons.BeaconScannerImpl;
import com.gigigo.orchextra.device.bluetooth.beacons.mapper.BeaconAndroidMapper;
import com.gigigo.orchextra.device.bluetooth.beacons.mapper.BeaconRegionAndroidMapper;
import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.MonitoringListener;
import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.MonitoringListenerImpl;
import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.RegionMonitoringScanner;
import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.RegionMonitoringScannerImpl;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.BeaconRangingScannerImpl;
import com.gigigo.orchextra.di.qualifiers.BeaconEventsInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.MainThread;
import com.gigigo.orchextra.di.qualifiers.RegionsProviderInteractorExecution;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusInfo;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.threads.ThreadSpec;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.logging.LogManager;
import org.altbeacon.beacon.logging.Loggers;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Provider;
import orchextra.javax.inject.Singleton;


@Module
public class BeaconsModule {

  @Provides @Singleton BeaconManager provideBeaconManager(ContextProvider contextProvider,
      OrchextraLogger orchextraLogger){
    BeaconManager beaconManager = BeaconManager.getInstanceForApplication(
        contextProvider.getApplicationContext());

    setBeaconsLogLevel(orchextraLogger);
    BeaconParser bp = new BeaconParser().setBeaconLayout(BuildConfig.IBEACON_LAYOUT_PARSING);
    beaconManager.getBeaconParsers().add(bp);
    return beaconManager;
  }

  private void setBeaconsLogLevel(OrchextraLogger orchextraLogger) {
    if (orchextraLogger.getOrchextraSDKLogLevel() == OrchextraSDKLogLevel.ALL){
      LogManager.setVerboseLoggingEnabled(true);
      LogManager.setLogger(Loggers.verboseLogger());
    }else{
      LogManager.setVerboseLoggingEnabled(false);
      LogManager.setLogger(Loggers.empty());
    }
  }

  @Provides @Singleton BackgroundPowerSaver BackgroundPowerSaver(ContextProvider contextProvider){
    return new BackgroundPowerSaver(contextProvider.getApplicationContext());
  }

  @Provides @Singleton BeaconScanner provideBeaconScanner(RegionMonitoringScanner regionMonitoringScanner,
      BeaconRangingScanner beaconRangingScanner, AppRunningMode appRunningMode,
      BluetoothStatusInfo bluetoothStatusInfo, ConfigObservable configObservable,
      OrchextraLogger orchextraLogger){

    return new BeaconScannerImpl(regionMonitoringScanner, beaconRangingScanner, bluetoothStatusInfo,
        appRunningMode, configObservable, orchextraLogger);
  }

  @Provides @Singleton BeaconRangingScanner provideBeaconRangingScanner(BeaconManager beaconManager,
      BeaconsController beaconsController, BeaconRegionAndroidMapper beaconRegionControlMapper,
      BeaconAndroidMapper beaconAndroidMapper, OrchextraLogger orchextraLogger){
    return new BeaconRangingScannerImpl(beaconManager, beaconsController, beaconRegionControlMapper,
        beaconAndroidMapper, orchextraLogger);
  }

  @Provides @Singleton RegionMonitoringScanner provideRegionMonitoringScanner(ContextProvider contextProvider,
    BeaconManager beaconManager, MonitoringListener monitoringListener, BeaconsController beaconsController,
      BeaconRegionAndroidMapper beaconRegionControlMapper, OrchextraLogger orchextraLogger){
    return new RegionMonitoringScannerImpl(contextProvider, beaconManager, monitoringListener,
        beaconsController, beaconRegionControlMapper, orchextraLogger);
  }

  @Provides @Singleton BeaconsController provideBeaconsController(
      InteractorInvoker interactorInvoker, ActionDispatcher actionDispatcher,
      @BeaconEventsInteractorExecution
      Provider<InteractorExecution> beaconsInteractorExecutionProvider,
      @RegionsProviderInteractorExecution Provider<InteractorExecution> regionsProviderInteractorExecutionProvider,
      ErrorLogger errorLogger, @MainThread ThreadSpec mainThreadSpec){

    return new BeaconsController(interactorInvoker, actionDispatcher, beaconsInteractorExecutionProvider,
        regionsProviderInteractorExecutionProvider, errorLogger, mainThreadSpec);
  }

  @Provides @Singleton MonitoringListener provideMonitoringListener(AppRunningMode appRunningMode,
      BeaconRangingScanner beaconRangingScanner, OrchextraLogger orchextraLogger){
    return new MonitoringListenerImpl(appRunningMode, beaconRangingScanner, orchextraLogger);
  }

  @Provides @Singleton BeaconRegionAndroidMapper provideBeaconRegionAndroidMapper(){
    return new BeaconRegionAndroidMapper();
  }

  @Provides @Singleton BeaconAndroidMapper provideBeaconAndroidMapper(){
    return new BeaconAndroidMapper();
  }

}
