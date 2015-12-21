package com.gigigo.orchextra.domain.entities.triggers;

import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.strategy.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.entities.triggers.strategy.GeoPointEventTypeBehaviourImpl;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ScanTrigger extends Trigger {

  public ScanTrigger(TriggerType triggerType, String id, Point point,
      PhoneStatusType phoneStatusType) {
    super(TriggerType.BARCODE, id, point, phoneStatusType);
  }

  @Override protected void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(null);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(null);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(0.0);
  }
}
