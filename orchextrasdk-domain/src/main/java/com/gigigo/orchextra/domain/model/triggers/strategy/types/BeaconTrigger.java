package com.gigigo.orchextra.domain.model.triggers.strategy.types;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointEventTypeBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconTrigger extends Trigger {

  private final BeaconDistanceType beaconDistanceType;

  public BeaconTrigger(String code, OrchextraPoint point, AppRunningModeType appRunningModeType,
      BeaconDistanceType beaconDistanceType, GeoPointEventType geoPointEventType) {
    super(TriggerType.BEACON, code, point, appRunningModeType);

    this.beaconDistanceType = beaconDistanceType;

  }

  public BeaconTrigger(OrchextraBeacon orchextraBeacon, AppRunningModeType appRunningMode) {
    super(TriggerType.BEACON, orchextraBeacon.getCode(), null, appRunningMode);
    this.beaconDistanceType = orchextraBeacon.getBeaconDistance();
  }

  @Override void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(beaconDistanceType);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(null);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(0.0);
  }

}
