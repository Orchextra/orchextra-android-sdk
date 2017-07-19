package com.gigigo.orchextra.domain.model.triggers.strategy.types;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraTLMEddyStoneBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.EddyStoneTlmBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointEventTypeBehaviourImpl;

/**
 * Created by nubor on 05/01/2017.
 */
public class EddyStoneBeaconTrigger extends Trigger {

  private final BeaconDistanceType beaconDistanceType;
  private final OrchextraTLMEddyStoneBeacon tlmData;


  public EddyStoneBeaconTrigger(OrchextraBeacon orchextraBeacon,
      AppRunningModeType appRunningMode) {
    super(TriggerType.EDDYSTONE, orchextraBeacon.getCode(), null, appRunningMode);
    this.beaconDistanceType = orchextraBeacon.getBeaconDistance();
    this.tlmData = orchextraBeacon.getTlmEddystone();
    this.tlmData.setUrl(orchextraBeacon.getUrl());

  }

  //asv this method no-sense refactor
  @Override void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(beaconDistanceType);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(null);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(0.0);
    this.eddyStoneTlmBehaviour = new EddyStoneTlmBehaviourImpl(this.tlmData);
  }

}