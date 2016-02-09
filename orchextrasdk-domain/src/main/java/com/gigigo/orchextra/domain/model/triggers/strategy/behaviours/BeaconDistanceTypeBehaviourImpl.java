package com.gigigo.orchextra.domain.model.triggers.strategy.behaviours;

import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconDistanceTypeBehaviourImpl implements BeaconDistanceTypeBehaviour {

  private BeaconDistanceType beaconDistanceType;

  public BeaconDistanceTypeBehaviourImpl(BeaconDistanceType beaconDistanceType) {
    this.beaconDistanceType = beaconDistanceType;
  }

  @Override public BeaconDistanceType getBeaconDistanceType() {
    return beaconDistanceType;
  }

  @Override public boolean isSupported() {
    return (beaconDistanceType==null)? false : true;
  }
}
