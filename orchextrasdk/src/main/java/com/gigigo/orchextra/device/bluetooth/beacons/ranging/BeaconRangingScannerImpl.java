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
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.device.bluetooth.beacons.mapper.BeaconAndroidMapper;
import com.gigigo.orchextra.device.bluetooth.beacons.mapper.BeaconRegionAndroidMapper;
import com.gigigo.orchextra.domain.abstractions.beacons.BackgroundBeaconsRangingTimeType;
import com.gigigo.orchextra.control.controllers.proximity.beacons.BeaconsController;
import com.gigigo.orchextra.device.bluetooth.beacons.ranging.exceptions.BulkRangingScannInBackgroundException;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;


  @Override public void stopRangingScanForDetectedRegion(Region region) {
    stopRangingRegion(region);
  }

  private void stopRangingAllRegions() {
    Iterator<Region> iterator = regions.iterator();
    while (iterator.hasNext()){
      try {
        Region region = iterator.next();
        beaconManager.stopRangingBeaconsInRegion(region);
        removeScheduledEndOfRanging(region);
        iterator.remove();
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
    updateBackgroudScanTimes(BeaconManager.DEFAULT_BACKGROUND_SCAN_PERIOD, BeaconManager.DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
    ranging = false;
    GGGLogImpl.log("Ranging stopped");
  }

  private void stopRangingRegion(Region region) {

    if (!regions.contains(region)){
      return;
    }

    try {
      beaconManager.stopRangingBeaconsInRegion(region);
      removeRangingRegion(region);

      GGGLogImpl.log("Ranging stopped in region: " + region.getUniqueId());
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    checkAvailableRegions();
  }

  private void removeRangingRegion(Region region) {
    removeScheduledEndOfRanging(region);
    regions.remove(region);
    if (regions.isEmpty()) {
      updateBackgroudScanTimes(BeaconManager.DEFAULT_BACKGROUND_SCAN_PERIOD,
          BeaconManager.DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
    }
  }

  private void removeScheduledEndOfRanging(Region region) {
    if (regionThreads.containsKey(region.getUniqueId())){
      Thread t = regionThreads.remove(region.getUniqueId());

      if (t!=null && t.isAlive()){
        GGGLogImpl.log("Thread " +t.toString() +"Will be Interrupted", LogLevel.WARN);
        t.interrupt();
      }
    }
  }

  private void updateBackgroudScanTimes(long scanPeriod, long beetweenScanPeriod) {

    try {
      if (beaconManager.isAnyConsumerBound()){
        beaconManager.setBackgroundScanPeriod(scanPeriod);
        beaconManager.setBackgroundBetweenScanPeriod(beetweenScanPeriod);
        beaconManager.updateScanPeriods();
      }
      GGGLogImpl.log("Update cycled Scan times");
    } catch (RemoteException e) {
      e.printStackTrace();
      GGGLogImpl.log("Unable to update cycled Scan times", LogLevel.ERROR);
    }


  }

  private void checkAvailableRegions() {
    if (regions.size()>0){
      for (Region region1 : regions){
        GGGLogImpl.log("Regions for scanning "+ regions.toString() +": " + region1.getUniqueId());
      }
    }else{
      ranging = false;
      GGGLogImpl.log("Regions to be ranged EMPTY: " + regions.toString());
    }
  }

  //endregion
  // region Background scanning time
  @Override public BackgroundBeaconsRangingTimeType getBackgroundBeaconsRangingTimeType() {
    if (this.backgroundBeaconsRangingTimeType == BackgroundBeaconsRangingTimeType.INFINITE) {
      GGGLogImpl.log("WARNING --> INFINITE Background Beacons Ranging Time Type could provoke "
          + "an extremely drain of you battery use MAX instead", LogLevel.WARN);
    }
    return this.backgroundBeaconsRangingTimeType;
  }

  @Override public boolean isRanging() {
    return ranging;
  }

  // endregion

  //endregion

}
