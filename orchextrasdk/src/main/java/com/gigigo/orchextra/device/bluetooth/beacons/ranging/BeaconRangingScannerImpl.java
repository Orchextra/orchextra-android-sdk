/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.device.bluetooth.beacons.ranging;

import android.os.RemoteException;

import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.control.controllers.proximity.beacons.BeaconsController;
import com.gigigo.orchextra.device.bluetooth.beacons.BeaconBackgroundModeScan;
import com.gigigo.orchextra.device.bluetooth.beacons.mapper.BeaconAndroidMapper;
import com.gigigo.orchextra.device.bluetooth.beacons.mapper.BeaconRegionAndroidMapper;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.exceptions.BulkRangingScannInBackgroundException;
import com.gigigo.orchextra.domain.abstractions.beacons.BackgroundBeaconsRangingTimeType;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.sdk.OrchextraManager;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BeaconRangingScannerImpl implements RangeNotifier, BeaconRangingScanner {

  private static BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType =
      BackgroundBeaconsRangingTimeType.getType(BuildConfig.BACKGROUND_BEACONS_RANGING_TIME);

  private final BeaconManager beaconManager;
  private final BeaconsController beaconsController;
  private final BeaconRegionAndroidMapper beaconRegionMapper;
  private final BeaconAndroidMapper beaconAndroidMapper;
  private final OrchextraLogger orchextraLogger;

  private HashMap<String, Thread> regionThreads = new HashMap<>();

  private boolean ranging = false;

  //avoid possible duplicates in region using set collection
  private Set<Region> regions = (Set<Region>) Collections.synchronizedSet(new HashSet<Region>());

  public BeaconRangingScannerImpl(BeaconManager beaconManager, BeaconsController beaconsController,
      BeaconRegionAndroidMapper beaconRegionMapper, BeaconAndroidMapper beaconAndroidMapper,
      OrchextraLogger orchextraLogger) {
    this.beaconManager = beaconManager;
    this.beaconsController = beaconsController;
    this.beaconRegionMapper = beaconRegionMapper;
    this.beaconAndroidMapper = beaconAndroidMapper;
    this.orchextraLogger = orchextraLogger;

    beaconManager.addRangeNotifier(this);
  }

  // region RangeNotifier Interface

  /**
   * Called from altbeacon Sdk when ranging is executed.
   *
   * @param collection beacons that are currently in range
   * @param region region which all the scanned beacons received belongs to
   */
  @Override public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
    List beaconsList = new ArrayList<>(collection);

    if (regions.contains(region)) {
      List<OrchextraBeacon> beacons = beaconAndroidMapper.externalClassListToModelList(beaconsList);
      beaconsController.onBeaconsDetectedInRegion(beacons);
    }

    if (collection.size() > 0) {
      for (Beacon beacon : collection) {
        //eddystone frames(uid2/eid2/url1), dont have all ids
        if (beacon != null) {
          String strBeacon = "Beacon: ";
          for (int i = 0; i < beacon.getIdentifiers().size(); i++) {
            strBeacon = strBeacon + beacon.getIdentifier(i);
          }
          orchextraLogger.log("Beacon: " + strBeacon);
        }
      }
    }
  }

  //endregion

  //region RegionsProvider Interface

  /**
   * This method will be called when regions are obtained from datasources and ready to be used
   *
   * @param regions regions to be scanned in ranging
   */
  @Override public void onRegionsReady(List<OrchextraRegion> regions) {
    List<Region> altRegions = beaconRegionMapper.modelListToExternalClassList(regions);
    this.regions.addAll(altRegions);
    initRanging(BackgroundBeaconsRangingTimeType.INFINITE);
  }

  //endregion

  //region BeaconRangingScanner Interface
  //region init ranging

  /**
   * This method MUST be called in only in foreground, because calling this method in background
   * could provoke an extremely drain of users battery.
   * <p>
   * This method init ranging scan without previous monitoring and region detection over all
   * existing regions. In addition this ranging scan will be enabled till the app changes mode
   * from background to foreground or viceversa, so be aware of calling this method when app is in
   * background.
   * <p>
   * All existing regions for app will be monitored at the same time using this method.
   *
   * @param appRunningModeType current running mode of app AppRunningModeType.BACKGROUND or
   * AppRunningModeType.FOREGROUND
   * @throws BulkRangingScannInBackgroundException when called on from background
   */
  @Override public void initRangingScanForAllKnownRegions(AppRunningModeType appRunningModeType) {
    if (OrchextraManager.getBackgroundModeScan()
        != BeaconBackgroundModeScan.HARDCORE.getIntensity()) {
      if (appRunningModeType == AppRunningModeType.BACKGROUND) {
        throw new BulkRangingScannInBackgroundException(
            "initRangingScanForAllKnownRegions " + "MUST be called only if app is in foreground");
      }
    } else {
      System.out.println(
          "BeaconBackgroundModeScan.HARDCORE, infinite Ranging in background allowed");
    }

    //callback argument into obtainRegionsToScan will call to onRegionsReady
    beaconsController.getAllRegionsFromDataBase(this);
  }

  /**
   * This method is called after the monitoring process discovers a Region, it saves the received
   * region as a region to scan and calls ranging process to start during the specified seconds.
   * <p>
   * The duration of the scan will depend on the AppRunningMode : FOREGROUND will scan indefinitely
   * till the app went to background and BACKGROUND mode can scan during 10 seconds as default, 180
   * seconds maximum or even not start the background ranging process.
   *
   * @param regions detected region
   * @param backgroundBeaconsRangingTimeType ranging time for scan
   * @throws BulkRangingScannInBackgroundException when called on from background
   * @See BackgroundBeaconsRangingTimeType
   * <p>
   * End of scanning can be provoked as well when the scanned region exit event is received
   * <p>
   * AppRunningModeType.FOREGROUND
   */
  @Override public void initRangingScanForDetectedRegion(List<Region> regions,
      BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType) {
    this.regions.addAll(regions);
    initRanging(backgroundBeaconsRangingTimeType);
  }

  private void initRanging(
      final BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType) {

    manageGeneralBackgroundScanTimes(backgroundBeaconsRangingTimeType);

    new Thread(new Runnable() {
      @Override public void run() {
        for (Region region : regions) {
          try {
            //asv in the future, if we want more eddystone frame, we need to
            //check if the region is eddystone region(getid1 length 20-22 chars)
            //and create a null null null region, because, the eddytone frame URL/EID/IBeacon
            //dont have ids like UID frame, enjoy!
            manageRegionBackgroundScanTime(region, backgroundBeaconsRangingTimeType);
            beaconManager.startRangingBeaconsInRegion(region);
            ranging = true;
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  //asv ver con beni
  private void manageGeneralBackgroundScanTimes(BackgroundBeaconsRangingTimeType time) {
    if (time == BackgroundBeaconsRangingTimeType.MAX) {
      updateBackgroundScanTimes(BuildConfig.BACKGROUND_BEACONS_SCAN_TIME,
          BuildConfig.BACKGROUND_BEACONS_BEETWEEN_SCAN_TIME);
    } else {
      updateBackgroundScanTimes(BeaconManager.DEFAULT_BACKGROUND_SCAN_PERIOD,
         BeaconManager.DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
    }
  }

  private void manageRegionBackgroundScanTime(Region region,
      BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType) {
    if (backgroundBeaconsRangingTimeType != BackgroundBeaconsRangingTimeType.INFINITE) {
      scheduleEndOfRanging(region, backgroundBeaconsRangingTimeType.getIntValue());
    }
  }

  private void scheduleEndOfRanging(final Region region, final int duration) {

    Thread t = new Thread(new Runnable() {
      @Override public void run() {
        try {
          Thread.sleep(duration);
          stopRangingRegion(region);
          regionThreads.remove(region.getUniqueId());
        } catch (InterruptedException e) {
          orchextraLogger.log("This interruption coulbe be provoked see log below",
              OrchextraSDKLogLevel.WARN);
        }
      }
    });
    regionThreads.put(region.getUniqueId(), t);
    t.start();
  }

  //endregion

  //region Stop Ranging

  /**
   * This method will terminate the scan over all scanned regions at the moment.
   */
  @Override public void stopAllCurrentRangingScannedRegions() {
    stopRangingAllRegions();
  }

  /**
   * This method will terminate the scan over the specified region. This method should be called on
   * monitoring exit event received over specific region.
   */
  @Override public void stopRangingScanForDetectedRegion(Region region) {
    stopRangingRegion(region);
  }

  private void stopRangingAllRegions() {
    Iterator<Region> iterator = regions.iterator();
    while (iterator.hasNext()) {
      try {
        Region region = iterator.next();
        beaconManager.stopRangingBeaconsInRegion(region);
        removeScheduledEndOfRanging(region);
        iterator.remove();
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    updateBackgroundScanTimes(BeaconManager.DEFAULT_BACKGROUND_SCAN_PERIOD,
        BeaconManager.DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
    ranging = false;
    orchextraLogger.log("Ranging stop");
  }

  private void stopRangingRegion(Region region) {

    if (!regions.contains(region)) {
      return;
    }

    try {
      beaconManager.stopRangingBeaconsInRegion(region);
      removeRangingRegion(region);

      orchextraLogger.log("Ranging stop in region: " + region.getUniqueId());
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    checkAvailableRegions();
  }

  private void removeRangingRegion(Region region) {
    removeScheduledEndOfRanging(region);
    regions.remove(region);
    if (regions.isEmpty()) {
      updateBackgroundScanTimes(BeaconManager.DEFAULT_BACKGROUND_SCAN_PERIOD,
          BeaconManager.DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
    }
  }

  private void removeScheduledEndOfRanging(Region region) {
    if (regionThreads.containsKey(region.getUniqueId())) {
      Thread t = regionThreads.remove(region.getUniqueId());

      if (t != null && t.isAlive()) {
        orchextraLogger.log("Thread " + t.toString() + "Will be Interrupted",
            OrchextraSDKLogLevel.WARN);
        t.interrupt();
      }
    }
  }


  @Override
  @Deprecated //asv this never used, the times not are the problem, for change time use BuildConfig
  public void updateBackgroundScanPeriodBetweenScans(long beetweenScanPeriod) {
    updateBackgroundScanTimes(BeaconManager.DEFAULT_BACKGROUND_SCAN_PERIOD,
        beetweenScanPeriod);
  }

  private void updateBackgroundScanTimes(long scanPeriod, long beetweenScanPeriod) {
    try {
      beaconManager.setBackgroundScanPeriod(scanPeriod);
      beaconManager.setBackgroundBetweenScanPeriod(beetweenScanPeriod);

      if (beaconManager.isAnyConsumerBound()) {
        beaconManager.updateScanPeriods();
      }

      orchextraLogger.log("Update cycled Scan times");
    } catch (RemoteException e) {
      e.printStackTrace();
      orchextraLogger.log("Unable to update cycled Scan times", OrchextraSDKLogLevel.ERROR);
    }
  }

  private void checkAvailableRegions() {
    if (regions.size() > 0) {
      for (Region region1 : regions) {
        orchextraLogger.log(
            "Regions for scanning " + regions.toString() + ": " + region1.getUniqueId());
      }
    } else {
      ranging = false;
      orchextraLogger.log("Regions to be ranged EMPTY: " + regions.toString());
    }
  }

  //endregion
  // region Background scanning time
  @Override public BackgroundBeaconsRangingTimeType getBackgroundBeaconsRangingTimeType() {
    if (OrchextraManager.getBackgroundModeScan()
        != BeaconBackgroundModeScan.HARDCORE.getIntensity()) {
      if (backgroundBeaconsRangingTimeType == BackgroundBeaconsRangingTimeType.INFINITE) {
        orchextraLogger.log(
            "WARNING --> INFINITE Background Beacons Ranging Time Type could provoke "
                + "an extremely drain of you battery use MAX instead", OrchextraSDKLogLevel.WARN);
      }
    }
    return backgroundBeaconsRangingTimeType;
  }

  @Override public boolean isRanging() {
    return ranging;
  }

  // endregion

  //endregion
}
