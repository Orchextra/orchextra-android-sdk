package com.gigigo.orchextra.device.bluetooth.beacons.monitoring;

import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import java.util.List;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public interface RegionMonitoringScanner extends RegionsProviderListener {
  void initMonitoring();
  void stopMonitoring();

  List<Region> obtainRegionsInRange();

  boolean isMonitoring();

  void setRunningMode(AppRunningModeType appRunningMode);

  void updateRegions(List deletedRegions, List newRegions);
}
