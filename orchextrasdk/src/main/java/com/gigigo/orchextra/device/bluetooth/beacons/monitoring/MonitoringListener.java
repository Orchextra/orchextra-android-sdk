package com.gigigo.orchextra.device.bluetooth.beacons.monitoring;

import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 26/1/16.
 */
public interface MonitoringListener {
  void onRegionEnter(Region region);
  void onRegionExit(Region region);
}
