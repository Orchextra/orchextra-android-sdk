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

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.RegionMonitoringScanner;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.exceptions.RangingScanInBackgroundException;
import com.gigigo.orchextra.domain.abstractions.beacons.BackgroundBeaconsRangingTimeType;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatus;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusInfo;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusListener;
import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.abstractions.observer.OrchextraChanges;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeaconUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;


public class BeaconScannerImpl implements BeaconScanner, Observer, BluetoothStatusListener {

  private final RegionMonitoringScanner regionMonitoringScanner;
  private final BeaconRangingScanner beaconRangingScanner;
  private final BluetoothStatusInfo bluetoothStatusInfo;
  private final AppRunningMode appRunningMode;
  private final ConfigObservable configObservable;

  public BeaconScannerImpl(RegionMonitoringScanner regionMonitoringScanner,
      BeaconRangingScanner beaconRangingScanner, BluetoothStatusInfo bluetoothStatusInfo,
      AppRunningMode appRunningMode, ConfigObservable configObservable) {

    this.regionMonitoringScanner = regionMonitoringScanner;
    this.beaconRangingScanner = beaconRangingScanner;
    this.bluetoothStatusInfo = bluetoothStatusInfo;
    this.appRunningMode = appRunningMode;
    this.configObservable = configObservable;

    this.configObservable.registerObserver(this);
  }

  @Override public void startMonitoring() {
    bluetoothStatusInfo.setBluetoothStatusListener(this);
    bluetoothStatusInfo.obtainBluetoothStatus();
  }

  private void initMonitoring() {
    regionMonitoringScanner.setRunningMode(appRunningMode.getRunningModeType());
    if (!regionMonitoringScanner.isMonitoring()){
      regionMonitoringScanner.initMonitoring();
    }

  }

  @Override public void startRangingScanner() {
    if (!beaconRangingScanner.isRanging()){
      beaconRangingScanner.initRangingScanForAllKnownRegions(appRunningMode.getRunningModeType());
    }
  }

  @Override public void initAvailableRegionsRangingScanner() {

    if (appRunningMode.getRunningModeType() == AppRunningModeType.BACKGROUND){
      throw new RangingScanInBackgroundException(
          "Infinite Ranging Scan in Background Mode is not allowed");
    }

    if (beaconRangingScanner.isRanging()){
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
    if (regionMonitoringScanner.isMonitoring()){
      regionMonitoringScanner.stopMonitoring();
    }
    this.configObservable.removeObserver(this);
  }

  @Override public void stopRangingScanner() {
    if (beaconRangingScanner.isRanging()){
      beaconRangingScanner.stopAllCurrentRangingScannedRegions();
    }
  }

  @Override public void onBluetoothStatus(BluetoothStatus bluetoothStatus) {
    switch (bluetoothStatus){
      case NO_BLTE_SUPPORTED:GGGLogImpl.log("CAUTION BLTE not supported, some features can not work as expected",
            LogLevel.WARN);
        // would be great to do something with error? like show a toast...
        break;
      case NO_PERMISSIONS:
        GGGLogImpl.log(
            "CAUTION Bluetooth permissions not granted, some features can not work as expected",
            LogLevel.WARN);
        // would be great to do something with error? like show a toast...
        break;
      case NOT_ENABLED:
        GGGLogImpl.log("CAUTION Bluetooth is off some features "
            + "cannot start to work till the user switches it on", LogLevel.WARN);
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

    if (observable instanceof ConfigObservable){

      OrchextraBeaconUpdates beaconUpdates = ((OrchextraUpdates)data).getOrchextraBeaconUpdates();

      if (beaconUpdates.hasChanges()){
        regionMonitoringScanner.updateRegions(beaconUpdates.getDeleteRegions(), beaconUpdates.getNewRegions());
      }
    }
  }

}
