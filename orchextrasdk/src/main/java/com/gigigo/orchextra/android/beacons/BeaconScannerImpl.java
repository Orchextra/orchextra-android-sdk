package com.gigigo.orchextra.android.beacons;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.android.beacons.monitoring.RegionMonitoringScanner;
import com.gigigo.orchextra.android.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.android.beacons.ranging.exceptions.RangingScanInBackgroundException;
import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class BeaconScannerImpl implements BeaconScanner, Observer, BluetoothStatusListener{

  private final RegionMonitoringScanner regionMonitoringScanner;
  private final BeaconRangingScanner beaconRangingScanner;
  private final BluetoothStatusInfo bluetoothStatusInfo;
  private final AppRunningMode appRunningMode;

  public BeaconScannerImpl(RegionMonitoringScanner regionMonitoringScanner,
      BeaconRangingScanner beaconRangingScanner, BluetoothStatusInfo bluetoothStatusInfo,
      AppRunningMode appRunningMode) {

    this.regionMonitoringScanner = regionMonitoringScanner;
    this.beaconRangingScanner = beaconRangingScanner;
    this.bluetoothStatusInfo = bluetoothStatusInfo;
    this.appRunningMode = appRunningMode;
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
  }

  @Override public void stopRangingScanner() {
    if (beaconRangingScanner.isRanging()){
      beaconRangingScanner.stopAllCurrentRangingScannedRegions();
    }
  }

  @Override public void onBluetoothStatus(BluetoothStatus bluetoothStatus) {
    switch (bluetoothStatus){
      case NO_BLTE_SUPPORTED:
        GGGLogImpl.log("CAUTION BLTE not supported, some features can not work as expected",
            LogLevel.WARN);
        //TODO do something with error
        break;
      case NO_PERMISSIONS:
        GGGLogImpl.log(
            "CAUTION Bluetooth permissions not granted, some features can not work as expected",
            LogLevel.WARN);
        //TODO do something with error
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
