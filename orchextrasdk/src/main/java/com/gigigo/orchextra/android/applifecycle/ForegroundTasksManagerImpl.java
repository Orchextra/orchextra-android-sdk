package com.gigigo.orchextra.android.applifecycle;

import com.gigigo.orchextra.android.beacons.BeaconScanner;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 28/1/16.
 */
public class ForegroundTasksManagerImpl implements  ForegroundTasksManager {

  private BeaconScanner beaconScanner;

  public ForegroundTasksManagerImpl(BeaconScanner beaconScanner) {
      this.beaconScanner = beaconScanner;
  }

  @Override public void startForegroundTasks() {
    beaconScanner.startMonitoring();
    beaconScanner.initAvailableRegionsRangingScanner();
  }

  @Override public void finalizeForegroundTasks() {
    beaconScanner.stopRangingScanner();
  }
}
