package com.gigigo.orchextra.android.mapper;

import com.gigigo.orchextra.domain.mappers.MapperAndroidListToModelList;
import com.gigigo.orchextra.domain.mappers.MapperAndroidToModel;
import com.gigigo.orchextra.domain.entities.BeaconDistance;
import com.gigigo.orchextra.domain.entities.OrchextraBeacon;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.Beacon;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class BeaconAndroidMapper implements MapperAndroidListToModelList<Beacon, OrchextraBeacon>,
    MapperAndroidToModel<Beacon, OrchextraBeacon>{

  private static final double IMMEDIATE_MAX_DISTANCE_THRESHOLD = 0.5;
  private static final double FAR_MIN_DISTANCE_THRESHOLD = 3.0;

  @Override public List<OrchextraBeacon> androidListToModelList(List<Beacon> beacons) {

    List<OrchextraBeacon> beaconList = new ArrayList<>();

    for (Beacon beacon:beacons){
      beaconList.add(androidToModel(beacon));
    }
    return beaconList;
  }

  @Override public OrchextraBeacon androidToModel(Beacon beacon) {
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
