package com.gigigo.orchextra.domain.background;

import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class BackgroundTasksManagerImpl implements BackgroundTasksManager {

  private final BeaconScanner beaconScanner;

  public BackgroundTasksManagerImpl(BeaconScanner beaconScanner) {
    this.beaconScanner = beaconScanner;
  }

  @Override public void startBackgroundTasks() {
      beaconScanner.startMonitoring();
  }

  @Override public void finalizeBackgroundTasks() {
    //beaconScanner.stopMonitoring();
  }

}
