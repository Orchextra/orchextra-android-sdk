package com.gigigo.orchextra.domain.entities.triggers;

import com.gigigo.orchextra.domain.entities.OrchextraPoint;
import com.gigigo.orchextra.domain.entities.triggers.strategy.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoPointEventTypeBehaviourImpl;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ScanTrigger extends Trigger {

  public ScanTrigger(TriggerType triggerType, String id, OrchextraPoint point,
      AppRunningModeType appRunningModeType) {
    super(triggerType, id, point, appRunningModeType);
  }

  @Override protected void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(null);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(null);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(0.0);
  }
}
