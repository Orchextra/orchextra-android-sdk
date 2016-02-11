package com.gigigo.orchextra.domain.model.triggers.strategy.types;

import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoDistanceBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointEventTypeBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.BeaconDistanceTypeBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointBehaviour;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public abstract class Trigger {

  //Mandatory
  private final TriggerType triggerType;
  //Mandatory
  private final String code;
  //Mandatory
  private final AppRunningModeType appRunningModeType;

  //Mandatory
  private final GeoPointBehaviour
      geoPointBehaviour;

  protected GeoPointEventTypeBehaviour geoPointEventTypeBehaviour;
  protected BeaconDistanceTypeBehaviour
      beaconDistanceTypeBehaviour;
  protected GeoDistanceBehaviour geoDistanceBehaviour;

  Trigger(TriggerType triggerType, String code, OrchextraPoint point, AppRunningModeType appRunningModeType) {
    this.triggerType = triggerType;
    this.code = code;
    this.appRunningModeType = appRunningModeType;
    this.geoPointBehaviour = new GeoPointBehaviourImpl(point);
  }

  abstract void setConcreteBehaviour();

  public static GeofenceTrigger createGeofenceTrigger(String id, OrchextraPoint point,
      AppRunningModeType appRunningModeType, double distance, GeoPointEventType geoPointEventType){

    GeofenceTrigger geofenceTrigger = new GeofenceTrigger(id, point, appRunningModeType, distance, geoPointEventType);
    geofenceTrigger.setConcreteBehaviour();
    return geofenceTrigger;
  }

  public static BeaconTrigger createBeaconTrigger(String id, OrchextraPoint point,
      AppRunningModeType appRunningModeType, BeaconDistanceType beaconDistType,
      GeoPointEventType geoPointEventType){

    BeaconTrigger beaconTrigger = new BeaconTrigger(id, point, appRunningModeType, beaconDistType, geoPointEventType);
    beaconTrigger.setConcreteBehaviour();
    return beaconTrigger;
  }

  public static ScanTrigger createQrScanTrigger(String id, OrchextraPoint point){
    return createScanTrigger(id,TriggerType.QR, point);
  }

  public static ScanTrigger createBarcodeScanTrigger(String id, OrchextraPoint point){
    return createScanTrigger(id,TriggerType.BARCODE, point);
  }

  public static ScanTrigger createVuforiaScanTrigger(String id, OrchextraPoint point){
    return createScanTrigger(id,TriggerType.VUFORIA, point);
  }

  private static ScanTrigger createScanTrigger(String id, TriggerType scanType, OrchextraPoint point){
    ScanTrigger scanTrigger = new ScanTrigger(scanType, id, point, AppRunningModeType.FOREGROUND);
    scanTrigger.setConcreteBehaviour();
    return scanTrigger;
  }

  public TriggerType getTriggerType() {
    return triggerType;
  }

  public String getCode() {
    return code;
  }

  public OrchextraPoint getPoint() {
    return geoPointBehaviour.getPoint();
  }

  public AppRunningModeType getAppRunningModeType() {
    return appRunningModeType;
  }

  public GeoPointEventType getGeoPointEventType() {
    return geoPointEventTypeBehaviour.getGeoPointEventType();
  }

  public BeaconDistanceType getBeaconDistanceType() {
    return beaconDistanceTypeBehaviour.getBeaconDistanceType();
  }

  public double getGeoDistance() {
    return geoDistanceBehaviour.getGeoDistance();
  }

  public boolean geoPointIsSupported(){
    return geoPointBehaviour.isSupported();
  }

  public static Trigger createBeaconRegionTrigger(AppRunningModeType appRunningMode,
      OrchextraRegion orchextraRegion) {
    BeaconRegionTrigger beaconRegionTrigger = new BeaconRegionTrigger(orchextraRegion, appRunningMode);
    beaconRegionTrigger.setConcreteBehaviour();
    return beaconRegionTrigger;
  }

  public static Trigger createBeaconTrigger(AppRunningModeType appRunningMode, OrchextraBeacon orchextraBeacon) {
    BeaconTrigger beaconTrigger = new BeaconTrigger(orchextraBeacon, appRunningMode);
    beaconTrigger.setConcreteBehaviour();
    return beaconTrigger;
  }
}
