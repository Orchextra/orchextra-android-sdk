package com.gigigo.orchextra.android.beacons.ranging;

import com.gigigo.orchextra.android.beacons.BackgroundBeaconsRangingTimeType;
import com.gigigo.orchextra.control.controllers.proximity.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;
import java.util.List;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public interface BeaconRangingScanner extends RegionsProviderListener {

  void initRangingScanForAllKnownRegions(AppRunningModeType appRunningModeType);
  void initRangingScanForDetectedRegion(List<Region> regions,
      BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType);

  void stopAllCurrentRangingScannedRegions();
  void stopRangingScanForDetectedRegion(Region region);

  BackgroundBeaconsRangingTimeType getBackgroundBeaconsRangingTimeType();

  boolean isRanging();
}
