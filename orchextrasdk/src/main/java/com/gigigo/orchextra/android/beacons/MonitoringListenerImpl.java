package com.gigigo.orchextra.android.beacons;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.android.beacons.monitoring.MonitoringListener;
import com.gigigo.orchextra.android.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 28/1/16.
 */
public class MonitoringListenerImpl implements MonitoringListener {

  private final AppRunningMode appRunningMode;
  private final BeaconRangingScanner beaconRangingScanner;
  private final BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType;

  public MonitoringListenerImpl(AppRunningMode appRunningMode,
      BeaconRangingScanner beaconRangingScanner) {

    this.appRunningMode = appRunningMode;
    this.beaconRangingScanner = beaconRangingScanner;
    this.backgroundBeaconsRangingTimeType = beaconRangingScanner.getBackgroundBeaconsRangingTimeType();
  }

  @Override public void onRegionEnter(Region region) {

    List<Region> regions = new ArrayList<>();
    regions.add(region);

    if (appRunningMode.getRunningModeType() == AppRunningModeType.FOREGROUND){

      beaconRangingScanner.initRangingScanForDetectedRegion(regions,
          BackgroundBeaconsRangingTimeType.INFINITE);

      GGGLogImpl.log("Ranging will be Started with infinite duration");

    }else if (appRunningMode.getRunningModeType() == AppRunningModeType.BACKGROUND &&
        backgroundBeaconsRangingTimeType != BackgroundBeaconsRangingTimeType.DISABLED){

      beaconRangingScanner.initRangingScanForDetectedRegion(regions,
          backgroundBeaconsRangingTimeType);

      GGGLogImpl.log("Ranging will be Started with " +
          String.valueOf(backgroundBeaconsRangingTimeType.getIntValue()) + " duration");

    }
  }

  @Override public void onRegionExit(Region region) {
    beaconRangingScanner.stopRangingScanForDetectedRegion(region);
  }
}
