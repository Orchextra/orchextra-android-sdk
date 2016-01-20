package com.gigigo.orchextra.domain.entities.triggers;

import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.strategy.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoPointEventTypeBehaviourImpl;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeofenceTrigger extends Trigger {

  private final double distance;
  private final GeoPointEventType geoPointEventType;

  public GeofenceTrigger(String id, Point point, AppRunningModeType appRunningModeType, double distance,
      GeoPointEventType geoPointEventType) {
    super(TriggerType.GEOFENCE, id, point, appRunningModeType);

    this.distance = distance;
    this.geoPointEventType = geoPointEventType;

  }

  @Override protected void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(null);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(geoPointEventType);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(distance);
  }
}
