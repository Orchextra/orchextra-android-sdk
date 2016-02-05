package com.gigigo.orchextra.android.beacons.monitoring;

import com.gigigo.orchextra.android.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;
import java.util.List;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public interface RegionMonitoringScanner extends RegionsProviderListener {
  void initMonitoring(AppRunningModeType appRunningModeType);
  void stopMonitoring();

  List<Region> obtainRegionsInRange();

  boolean isMonitoring();
}
