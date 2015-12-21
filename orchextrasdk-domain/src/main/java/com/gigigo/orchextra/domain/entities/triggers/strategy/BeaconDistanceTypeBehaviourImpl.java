package com.gigigo.orchextra.domain.entities.triggers.strategy;

import com.gigigo.orchextra.domain.entities.triggers.BeaconDistanceType;

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
