package com.gigigo.orchextra.sdk;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public class OrchextraTasksManagerImpl implements OrchextraTasksManager{

  private static final int FOREGROUND = 1;
  private static final int BACKGROUND = 2;

  private final BeaconScanner beaconScanner;
  private final ConfigDelegateImp configDelegateImp;
  private final GeofenceRegister geofenceRegister;

  public OrchextraTasksManagerImpl(
      BeaconScanner beaconScanner,
      ConfigDelegateImp configDelegateImp,
      GeofenceRegister geofenceRegister) {

    this.beaconScanner = beaconScanner;
    this.configDelegateImp = configDelegateImp;
    this.geofenceRegister = geofenceRegister;

  }

  @Override public void initBackgroundTasks() {
    initTasks(BACKGROUND);
  }

  @Override public void initForegroundTasks() {
    initTasks(FOREGROUND);
  }

  private void initTasks(int appRunningMode) {
    GGGLogImpl.log("Generic tasks have been started: Monitoring and Geofences");

    beaconScanner.startMonitoring();
    geofenceRegister.startGeofenceRegister();

    if (appRunningMode == FOREGROUND){
      GGGLogImpl.log("Foreground tasks have been started: Ranging and Request config");
      beaconScanner.initAvailableRegionsRangingScanner();
      configDelegateImp.sendConfiguration();
    }
  }

  @Override public void stopAllTasks() {
    beaconScanner.stopMonitoring();
    beaconScanner.stopRangingScanner();
    geofenceRegister.stopGeofenceRegister();
    geofenceRegister.clearGeofences();
  }

  @Override public void stopBackgroundServices() {
//background Services have been already stopped at AppStatusEventsListenerImpl.onBackgroundEnd()
  }

  @Override public void stopForegroundTasks() {
    beaconScanner.stopRangingScanner();
  }

}
