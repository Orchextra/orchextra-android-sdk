package com.gigigo.orchextra.android.beacons.model;

import org.altbeacon.beacon.Beacon;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class OrchextraBeaconMapper {

  private static final double IMMEDIATE_MAX_DISTANCE_THRESHOLD = 0.5;
  private static final double FAR_MIN_DISTANCE_THRESHOLD = 3.0;

  public OrchextraBeacon map(Beacon beacon) {
    return  new OrchextraBeacon(beacon.getId1().toString(), beacon.getId2().toInt(),
        beacon.getId3().toInt(), getDistance(beacon.getDistance()));
  }

  private BeaconDistance getDistance(double distance) {
    if (distance < IMMEDIATE_MAX_DISTANCE_THRESHOLD) {
      return BeaconDistance.IMMEDIATE;
    }
    else if(distance < FAR_MIN_DISTANCE_THRESHOLD) {
      return BeaconDistance.NEAR;
    }
    else {
      return BeaconDistance.FAR;
    }
  }

}
