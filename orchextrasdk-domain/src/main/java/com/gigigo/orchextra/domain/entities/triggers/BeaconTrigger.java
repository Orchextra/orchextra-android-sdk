package com.gigigo.orchextra.domain.entities.triggers;

import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.strategy.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoPointEventTypeBehaviourImpl;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconTrigger extends Trigger {

  private final BeaconDistanceType beaconDistanceType;
  private final GeoPointEventType geoPointEventType;

  public BeaconTrigger(String id, Point point, AppRunningModeType appRunningModeType,
      BeaconDistanceType beaconDistanceType, GeoPointEventType geoPointEventType) {
    super(TriggerType.BEACON, id, point, appRunningModeType);

    this.beaconDistanceType = beaconDistanceType;
    this.geoPointEventType = geoPointEventType;

  }

  @Override protected void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(beaconDistanceType);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(geoPointEventType);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(0.0);
  }

}
