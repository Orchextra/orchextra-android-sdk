package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.android.beacons.BeaconScanner;
import com.gigigo.orchextra.android.beacons.monitoring.MonitoringListener;
import com.gigigo.orchextra.android.beacons.monitoring.RegionMonitoringScanner;
import com.gigigo.orchextra.android.beacons.ranging.BeaconRangingScanner;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface BeaconsModuleProvider {
  BackgroundPowerSaver provideBackgroundPowerSaver();
  BeaconManager provideBeaconManager();
  BeaconScanner provideBeaconScanner();
  BeaconRangingScanner provideBeaconRangingScanner();
  RegionMonitoringScanner provideRegionMonitoringScanner();
  MonitoringListener provideMonitoringListener();
}
