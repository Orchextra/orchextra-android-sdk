package com.gigigo.orchextra.sdk.application;

import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 28/1/16.
 */
public class ForegroundTasksManagerImpl implements ForegroundTasksManager {

  private final BeaconScanner beaconScanner;
  private final ConfigDelegateImp configDelegateImp;

  public ForegroundTasksManagerImpl(BeaconScanner beaconScanner, ConfigDelegateImp configDelegateImp) {
      this.beaconScanner = beaconScanner;
      this.configDelegateImp = configDelegateImp;
  }

  @Override public void startForegroundTasks() {
    beaconScanner.startMonitoring();
    beaconScanner.initAvailableRegionsRangingScanner();

    configDelegateImp.sendConfiguration();
  }

  @Override public void finalizeForegroundTasks() {
    beaconScanner.stopRangingScanner();
  }
}
