package com.gigigo.orchextra.domain.model.triggers.strategy.types;

import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointEventTypeBehaviourImpl;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeofenceTrigger extends Trigger {

  private final double distance;
  private final GeoPointEventType geoPointEventType;

  public GeofenceTrigger(String code, OrchextraPoint point, AppRunningModeType appRunningModeType,
      double distance, GeoPointEventType geoPointEventType) {
    super(TriggerType.GEOFENCE, code, point, appRunningModeType);

    this.distance = distance;
    this.geoPointEventType = geoPointEventType;
    this.isTriggerable = true;
  }

  @Override void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(null);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(geoPointEventType);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(distance);
  }
}
