package com.gigigo.orchextra.domain.entities.triggers;

import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.strategy.BeaconDistanceTypeBehaviour;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoDistanceBehaviour;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoPointBehaviour;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoPointBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoPointEventTypeBehaviour;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public abstract class Trigger {

  //Mandatory
  private final TriggerType triggerType;
  //Mandatory
  private final String id;
  //Mandatory
  private final PhoneStatusType phoneStatusType;

  //Mandatory
  private final GeoPointBehaviour geoPointBehaviour;

  protected GeoPointEventTypeBehaviour geoPointEventTypeBehaviour;
  protected BeaconDistanceTypeBehaviour beaconDistanceTypeBehaviour;
  protected GeoDistanceBehaviour geoDistanceBehaviour;

  Trigger(TriggerType triggerType, String id, Point point, PhoneStatusType phoneStatusType) {
    this.triggerType = triggerType;
    this.id = id;
    this.phoneStatusType = phoneStatusType;
    this.geoPointBehaviour = new GeoPointBehaviourImpl(point);
  }

  protected abstract void setConcreteBehaviour();

  public static GeofenceTrigger createGeofenceTrigger(String id, Point point,
      PhoneStatusType phoneStatusType, double distance, GeoPointEventType geoPointEventType){

    GeofenceTrigger geofenceTrigger = new GeofenceTrigger(id, point, phoneStatusType, distance, geoPointEventType);
    geofenceTrigger.setConcreteBehaviour();
    return geofenceTrigger;
  }

  public static BeaconTrigger createBeaconTrigger(String id, Point point,
      PhoneStatusType phoneStatusType, BeaconDistanceType beaconDistType,
      GeoPointEventType geoPointEventType){

    BeaconTrigger beaconTrigger = new BeaconTrigger(id, point, phoneStatusType, beaconDistType, geoPointEventType);
    beaconTrigger.setConcreteBehaviour();
    return beaconTrigger;
  }

  public static ScanTrigger createQrScanTrigger(String id, Point point){
    return createScanTrigger(id,TriggerType.QR, point);
  }

  public static ScanTrigger createBarcodeScanTrigger(String id, Point point){
    return createScanTrigger(id,TriggerType.BARCODE, point);
  }

  public static ScanTrigger createVuforiaScanTrigger(String id, Point point){
    return createScanTrigger(id,TriggerType.VUFORIA, point);
  }

  private static ScanTrigger createScanTrigger(String id, TriggerType scanType, Point point){
    ScanTrigger scanTrigger = new ScanTrigger(scanType, id, point, PhoneStatusType.FOREGROUND);
    scanTrigger.setConcreteBehaviour();
    return scanTrigger;
  }

  public TriggerType getTriggerType() {
    return triggerType;
  }

  public String getId() {
    return id;
  }

  public Point getPoint() {
    return geoPointBehaviour.getPoint();
  }

  public PhoneStatusType getPhoneStatusType() {
    return phoneStatusType;
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

}
