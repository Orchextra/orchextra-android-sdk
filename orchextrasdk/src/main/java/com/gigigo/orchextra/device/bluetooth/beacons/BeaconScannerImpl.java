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

package com.gigigo.orchextra.device.bluetooth.beacons;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.RegionMonitoringScanner;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.exceptions.RangingScanInBackgroundException;
import com.gigigo.orchextra.domain.abstractions.beacons.BackgroundBeaconsRangingTimeType;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatus;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusInfo;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusListener;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.abstractions.observer.OrchextraChanges;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegionUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.sdk.OrchextraManager;

public class BeaconScannerImpl implements BeaconScanner, Observer, BluetoothStatusListener {

  private final RegionMonitoringScanner regionMonitoringScanner;
  private final BeaconRangingScanner beaconRangingScanner;
  private final BluetoothStatusInfo bluetoothStatusInfo;
  private final AppRunningMode appRunningMode;
  private final ConfigObservable configObservable;
  private final OrchextraLogger orchextraLogger;
  private boolean isRegisteredAsObserver = false;

  public BeaconScannerImpl(RegionMonitoringScanner regionMonitoringScanner,
      BeaconRangingScanner beaconRangingScanner, BluetoothStatusInfo bluetoothStatusInfo,
      AppRunningMode appRunningMode, ConfigObservable configObservable,
      OrchextraLogger orchextraLogger) {

    this.regionMonitoringScanner = regionMonitoringScanner;
    this.beaconRangingScanner = beaconRangingScanner;
    this.bluetoothStatusInfo = bluetoothStatusInfo;
    this.appRunningMode = appRunningMode;
    this.configObservable = configObservable;
    this.orchextraLogger = orchextraLogger;
  }

  private void registerAsObserver() {
    if (!isRegisteredAsObserver) {
      this.configObservable.registerObserver(this);
      isRegisteredAsObserver = true;
    }
  }

  private void unregisterAsObserver() {
    if (isRegisteredAsObserver) {
      this.configObservable.removeObserver(this);
      isRegisteredAsObserver = false;
    }
  }

  @Override public void startMonitoring() {
    registerAsObserver();

    bluetoothStatusInfo.setBluetoothStatusListener(this);
    bluetoothStatusInfo.obtainBluetoothStatus();
  }

  private void initMonitoring() {
    regionMonitoringScanner.setRunningMode(appRunningMode.getRunningModeType());
    if (!regionMonitoringScanner.isMonitoring()) {
      regionMonitoringScanner.initMonitoring();
    }
  }

  @Override public void startRangingScanner() {
    if (!beaconRangingScanner.isRanging()) {
      beaconRangingScanner.initRangingScanForAllKnownRegions(appRunningMode.getRunningModeType());
    }
  }

  @Override public void initAvailableRegionsRangingScanner() {
    //OXBEASEN feature 4 mcd
    //test that, remenber the reset of the background times...
    if (OrchextraManager.getBackgroundModeScan()
        != BeaconBackgroundModeScan.HARDCORE.getIntensity()) {
      if (appRunningMode.getRunningModeType() == AppRunningModeType.BACKGROUND) {
        throw new RangingScanInBackgroundException(
            "Infinite Ranging Scan in Background Mode is only allowed if your app set BeaconBackgroundModeScan.HARDCORE");
      }
    } else {
      System.out.println(
          "BeaconBackgroundModeScan.HARDCORE, infinite Ranging in background allowed");
    }
    //wen need to reeboot ranging process, because the transition between background to foreground,
    //change the rangin time from 10sg in background to infinite time
    //if you are inside region and the app in foreground
    if (beaconRangingScanner.isRanging()) {
      beaconRangingScanner.stopAllCurrentRangingScannedRegions();
    }

    beaconRangingScanner.initRangingScanForDetectedRegion(
        regionMonitoringScanner.obtainRegionsInRange(), BackgroundBeaconsRangingTimeType.INFINITE);
  }

  private void restartBeaconScanner() {
    stopRangingScanner();
    stopMonitoring();
    startMonitoring();
  }

  @Override public void stopMonitoring() {
    if (regionMonitoringScanner.isMonitoring()) {
      regionMonitoringScanner.stopMonitoring();
    }
    unregisterAsObserver();
  }

  @Override public void stopRangingScanner() {
    if (beaconRangingScanner.isRanging()) {
      beaconRangingScanner.stopAllCurrentRangingScannedRegions();
    }
  }

  @Override public void onBluetoothStatus(BluetoothStatus bluetoothStatus) {
    switch (bluetoothStatus) {
      case NO_BLTE_SUPPORTED:
        orchextraLogger.log("CAUTION BLTE not supported, some features can not work as expected",
            OrchextraSDKLogLevel.WARN);
        // would be great to do something with error? like show a toast...
        break;
      case NO_PERMISSIONS:
        orchextraLogger.log(
            "CAUTION Bluetooth permissions not granted, some features can not work as expected",
            OrchextraSDKLogLevel.WARN);
        // would be great to do something with error? like show a toast...
        break;
      case NOT_ENABLED:
        orchextraLogger.log("CAUTION Bluetooth is off some features "
            + "cannot start to work till the user switches it on", OrchextraSDKLogLevel.WARN);
        //IMPORTANT: DO NOT "break;", SCAN SHOULD START AUTOMATICALLY WHEN BLUETOOTH IS SWITCHED ON
      case READY_FOR_SCAN:
        initMonitoring();
      default:
        break;
    }
  }

  public boolean isRanging() {
    return beaconRangingScanner.isRanging();
  }

  public boolean isMonitoring() {
    return regionMonitoringScanner.isMonitoring();
  }

  @Override public void update(OrchextraChanges observable, Object data) {

    if (observable instanceof ConfigObservable) {

      OrchextraRegionUpdates beaconUpdates = ((OrchextraUpdates) data).getOrchextraRegionUpdates();

      if (beaconUpdates.hasChanges()) {
        regionMonitoringScanner.updateRegions(beaconUpdates.getDeleteRegions(),
            beaconUpdates.getNewRegions());
      }
    }
  }
}
