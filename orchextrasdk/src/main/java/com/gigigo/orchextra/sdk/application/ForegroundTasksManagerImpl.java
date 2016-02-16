package com.gigigo.orchextra.sdk.application;

import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 28/1/16.
 */
public class ForegroundTasksManagerImpl implements ForegroundTasksManager {

  private final BeaconScanner beaconScanner;
  private final ConfigDelegateImp configDelegateImp;
  private final GeofenceRegister geofenceRegister;

  public ForegroundTasksManagerImpl(BeaconScanner beaconScanner,
                                    ConfigDelegateImp configDelegateImp,
                                    GeofenceRegister geofenceRegister) {
    this.beaconScanner = beaconScanner;
    this.configDelegateImp = configDelegateImp;
    this.geofenceRegister = geofenceRegister;
  }

  @Override public void startForegroundTasks() {
    beaconScanner.startMonitoring();
    beaconScanner.initAvailableRegionsRangingScanner();

    geofenceRegister.startGeofenceRegister();

    configDelegateImp.sendConfiguration();
  }

  @Override public void finalizeForegroundTasks() {
    beaconScanner.stopRangingScanner();
  }
}
