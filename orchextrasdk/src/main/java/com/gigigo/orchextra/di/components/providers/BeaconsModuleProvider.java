package com.gigigo.orchextra.di.components.providers;

import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.MonitoringListener;
import com.gigigo.orchextra.device.bluetooth.beacons.monitoring.RegionMonitoringScanner;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
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
