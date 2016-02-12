package com.gigigo.orchextra.device.bluetooth.beacons.ranging;

import android.os.RemoteException;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.device.bluetooth.beacons.fake.BeaconRegionsFactory;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class BeaconRangingScannerImpl implements RangeNotifier, BeaconRangingScanner{

  //TODO SET OFICIAL TIME USING CONFIG
  //private BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType = BackgroundBeaconsRangingTimeType.DISABLED;
  //private BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType = BackgroundBeaconsRangingTimeType.MIN;
  private BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType = BackgroundBeaconsRangingTimeType.MAX;
  private final BeaconManager beaconManager;
  private final BeaconsController beaconsController;
  private final BeaconRegionAndroidMapper beaconRegionMapper;
  private final BeaconAndroidMapper beaconAndroidMapper;
  private boolean ranging = false;

  //avoid possible duplicates in region using set collection
  private Set<Region> regions = (Set<Region>) Collections.synchronizedSet(new HashSet<Region>());

  public BeaconRangingScannerImpl(BeaconManager beaconManager, BeaconsController beaconsController,
      BeaconRegionAndroidMapper beaconRegionMapper, BeaconAndroidMapper beaconAndroidMapper) {
    this.beaconManager = beaconManager;
    this.beaconsController = beaconsController;
    this.beaconRegionMapper = beaconRegionMapper;
    this.beaconAndroidMapper = beaconAndroidMapper;
    beaconManager.setRangeNotifier(this);
  }

  // region RangeNotifier Interface

  /**
   * Called from altbeacon Sdk when ranging is executed.
   * @param collection beacons that are currently in range
   * @param region region which all the scanned beacons received belongs to
   */
  @Override public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
    List beaconsList = new ArrayList<>(collection);
    List<OrchextraBeacon> beacons = beaconAndroidMapper.externalClassListToModelList(beaconsList);
    beaconsController.onBeaconsDetectedInRegion(beacons, beaconRegionMapper.externalClassToModel(
        region));

    if (collection.size() > 0) {
      for (Beacon beacon : collection) {
        GGGLogImpl.log("Beacon: "
                + beacon.getId1()
                + " major id:"
                + beacon.getId2()
                + "  minor id: "
                + beacon.getId3());
      }
    }
  }

  //endregion

  //region RegionsProvider Interface

  /**
   * This method will be called when regions are obtained from datasources and ready to be used
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
   *
   * This method init ranging scan without previous monitoring and region detection over all
   * existing regions. In addition this ranging scan will be enabled till the app changes mode
   * from background to foreground or viceversa, so be aware of calling this method when app is in
   * background.
   *
   * All existing regions for app will be monitored at the same time using this method.
   *
   * @param appRunningModeType current running mode of app AppRunningModeType.BACKGROUND or
   * AppRunningModeType.FOREGROUND
   *
   * @throws BulkRangingScannInBackgroundException when called on from background
   */
  @Override public void initRangingScanForAllKnownRegions(AppRunningModeType appRunningModeType) {

    if (appRunningModeType == AppRunningModeType.BACKGROUND){
      throw new BulkRangingScannInBackgroundException("initRangingScanForAllKnownRegions "
          + "MUST be called only if app is in foreground");
    }

    //callback argument into obtainRegionsToScan will call to onRegionsReady
    beaconsController.getAllRegionsFromDataBase(this);

    //TODO remove this line below when above ready
    //BeaconRegionsFactory.obtainRegionsToScan(this);
  }


  /**
   * This method is called after the monitoring process discovers a Region, it saves the received
   * region as a region to scan and calls ranging process to start during the specified seconds.
   *
   * The duration of the scan will depend on the AppRunningMode : FOREGROUND will scan indefinitely
   * till the app went to background and BACKGROUND mode can scan during 10 seconds as default, 180
   * seconds maximum or even not start the background ranging process.
   * @See BackgroundBeaconsRangingTimeType
   *
   * End of scanning can be provoked as well when the scanned region exit event is received
   *
   * AppRunningModeType.FOREGROUND
   * @param regions detected region
   * @param backgroundBeaconsRangingTimeType ranging time for scan
   *
   * @throws BulkRangingScannInBackgroundException when called on from background
   */
  @Override public void initRangingScanForDetectedRegion(List<Region> regions,
      BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType) {

    this.regions.addAll(regions);

    initRanging(backgroundBeaconsRangingTimeType);
  }

  private void initRanging(BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType) {
    for (Region region:regions){

      try {
        beaconManager.startRangingBeaconsInRegion(region);
        ranging = true;
        if (backgroundBeaconsRangingTimeType != BackgroundBeaconsRangingTimeType.INFINITE){
          scheduleEndOfRanging(region, backgroundBeaconsRangingTimeType.getIntValue());
        }
      } catch (RemoteException e) {
        e.printStackTrace();
      }

    }
  }

  private void scheduleEndOfRanging(final Region region, final int duration) {

    Thread t = new Thread(new Runnable() {
      @Override public void run() {
        try {
          Thread.sleep(duration);
          stopRangingRegion(region);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

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
    while (iterator.hasNext()){
      try {
        beaconManager.stopRangingBeaconsInRegion(iterator.next());
        iterator.remove();
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
    ranging = false;
    GGGLogImpl.log("Ranging stopped");
  }

  private void stopRangingRegion(Region region) {

    if (!regions.contains(region)){
      return;
    }

    try {
      beaconManager.stopRangingBeaconsInRegion(region);
      regions.remove(region);
      GGGLogImpl.log("Ranging stopped in region: " + region.getUniqueId());
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    checkAvailableRegions();
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
