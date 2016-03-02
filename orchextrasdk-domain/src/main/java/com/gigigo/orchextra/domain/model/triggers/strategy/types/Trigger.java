package com.gigigo.orchextra.domain.model.triggers.strategy.types;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.BeaconDistanceTypeBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoDistanceBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointEventTypeBehaviour;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

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
  protected boolean isTriggerable;

  //Mandatory
  private final GeoPointBehaviour geoPointBehaviour;

  protected GeoPointEventTypeBehaviour geoPointEventTypeBehaviour;
  protected BeaconDistanceTypeBehaviour beaconDistanceTypeBehaviour;
  protected GeoDistanceBehaviour geoDistanceBehaviour;

  Trigger(TriggerType triggerType, String code, OrchextraPoint point,
      AppRunningModeType appRunningModeType) {
    this.triggerType = triggerType;
    this.code = code;
    this.appRunningModeType = appRunningModeType;
    this.geoPointBehaviour = new GeoPointBehaviourImpl(point);
  }

  public Trigger() {
    this.triggerType = null;
    this.code = null;
    this.appRunningModeType = null;
    this.geoPointBehaviour = null;
  }

  abstract void setConcreteBehaviour();

  public static GeofenceTrigger createGeofenceTrigger(String code, OrchextraPoint point,
      AppRunningModeType appRunningModeType, double distance, GeoPointEventType geoPointEventType) {

    GeofenceTrigger geofenceTrigger =
        new GeofenceTrigger(code, point, appRunningModeType, distance, geoPointEventType);
    geofenceTrigger.setConcreteBehaviour();
    return geofenceTrigger;
  }

  @Deprecated public static BeaconTrigger createBeaconTrigger(String id, OrchextraPoint point,
      AppRunningModeType appRunningModeType, BeaconDistanceType beaconDistType,
      GeoPointEventType geoPointEventType) {

    BeaconTrigger beaconTrigger =
        new BeaconTrigger(id, point, appRunningModeType, beaconDistType, geoPointEventType);
    beaconTrigger.setConcreteBehaviour();
    return beaconTrigger;
  }

  public static Trigger createQrScanTrigger(String id, OrchextraPoint point) {
    return createScanTrigger(id, TriggerType.QR, point);
  }

  public static Trigger createBarcodeScanTrigger(String id, OrchextraPoint point) {
    return createScanTrigger(id, TriggerType.BARCODE, point);
  }

  public static Trigger createVuforiaScanTrigger(String id, OrchextraPoint point) {
    return createScanTrigger(id, TriggerType.VUFORIA, point);
  }

  private static Trigger createScanTrigger(String id, TriggerType scanType, OrchextraPoint point) {
    try {
      ScanTrigger scanTrigger = new ScanTrigger(scanType, id, point, AppRunningModeType.FOREGROUND);
      scanTrigger.setConcreteBehaviour();
      return scanTrigger;
    } catch (Exception e) {
      return Trigger.createEmptyTrigger();
    }
  }

  public static Trigger createBeaconRegionTrigger(AppRunningModeType appRunningMode,
      OrchextraRegion orchextraRegion) {

    try {
      BeaconRegionTrigger beaconRegionTrigger =
          new BeaconRegionTrigger(orchextraRegion, appRunningMode);
      beaconRegionTrigger.setConcreteBehaviour();
      return beaconRegionTrigger;
    } catch (Exception e) {
      return Trigger.createEmptyTrigger();
    }
  }

  public static Trigger createBeaconTrigger(AppRunningModeType appRunningMode,
      OrchextraBeacon orchextraBeacon) {
    try {
      BeaconTrigger beaconTrigger = new BeaconTrigger(orchextraBeacon, appRunningMode);
      beaconTrigger.setConcreteBehaviour();
      return beaconTrigger;
    } catch (Exception e) {
      return Trigger.createEmptyTrigger();
    }
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

  public boolean beaconDistanceTypeIsSupported() {
    return beaconDistanceTypeBehaviour.isSupported();
  }

  public double getGeoDistance() {
    return geoDistanceBehaviour.getGeoDistance();
  }

  public boolean geoPointIsSupported() {
    return geoPointBehaviour.isSupported();
  }

  private static Trigger createEmptyTrigger() {
    Trigger trigger = new Trigger() {
      @Override void setConcreteBehaviour() {
        this.beaconDistanceTypeBehaviour = null;
        this.geoPointEventTypeBehaviour = null;
        this.geoDistanceBehaviour = null;
      }
    };
    return trigger;
  }
}
