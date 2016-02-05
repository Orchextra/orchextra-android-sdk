package com.gigigo.orchextra.android.beacons;

import android.util.Log;
import com.gigigo.orchextra.android.beacons.monitoring.RegionMonitoringScanner;
import com.gigigo.orchextra.android.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.android.beacons.ranging.exceptions.RangingScanInBackgroundException;
import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.entities.config.Config;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;
import org.altbeacon.beacon.BeaconManager;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class BeaconScannerImpl implements BeaconScanner, Observer, BluetoothStatusListener{

  private static final String TAG = "BeaconScannerImpl";

  private final RegionMonitoringScanner regionMonitoringScanner;
  private final BeaconRangingScanner beaconRangingScanner;
  private final BeaconManager beaconManager;
  private final BluetoothStatusInfo bluetoothStatusInfo;
  private final AppRunningMode appRunningMode;

  public BeaconScannerImpl(RegionMonitoringScanner regionMonitoringScanner,
      BeaconRangingScanner beaconRangingScanner, BeaconManager beaconManager,
      BluetoothStatusInfo bluetoothStatusInfo, AppRunningMode appRunningMode) {

    this.regionMonitoringScanner = regionMonitoringScanner;
    this.beaconRangingScanner = beaconRangingScanner;
    this.beaconManager = beaconManager;
    this.bluetoothStatusInfo = bluetoothStatusInfo;
    this.appRunningMode = appRunningMode;
  }

  @Override public void startMonitoring() {
    bluetoothStatusInfo.setBluetoothStatusListener(this);
    bluetoothStatusInfo.obtainBluetoothStatus();
  }

  private void initMonitoring() {
    beaconManager.setBackgroundMode(
        appRunningMode.getRunningModeType() == AppRunningModeType.BACKGROUND);

    if (!regionMonitoringScanner.isMonitoring()){
      regionMonitoringScanner.initMonitoring(appRunningMode.getRunningModeType());
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
  }

  @Override public void stopRangingScanner() {
    if (beaconRangingScanner.isRanging()){
      beaconRangingScanner.stopAllCurrentRangingScannedRegions();
    }
  }

  @Override public void onBluetoothStatus(BluetoothStatus bluetoothStatus) {
    switch (bluetoothStatus){
      case NO_BLTE_SUPPORTED:
        Log.w(TAG, "LOG: CAUTION BLTE not supported, some features can not work as expected");
        //TODO do something with error
        break;
      case NO_PERMISSIONS:
        Log.w(TAG, "LOG: CAUTION Bluetooth permissions not granted, some features can not work as expected");
        //TODO do something with error
        break;
      case NOT_ENABLED:
        Log.w(TAG, "LOG: CAUTION Bluetooth is off some features cannot start to work till the user switches it on");
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

  @Override public void update(Subject observable, Object data) {

    if (observable instanceof ConfigObservable){

      ConfigInfoResult configInfoResult = (ConfigInfoResult) data;

      BeaconsData beaconsData = new BeaconsData();
      //BeaconsData beaconsData = configInfoResult.beaconsHasChanged();

      if (beaconsData.hasChanged()){
        restartBeaconScanner();
      }
    }
  }

}
