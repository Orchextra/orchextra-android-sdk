package com.gigigo.orchextra.domain.background;

import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
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
