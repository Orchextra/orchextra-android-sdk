package com.gigigo.orchextra.device.bluetooth.beacons.mapper;

import com.gigigo.gggjavalib.general.utils.Hashing;
import com.gigigo.ggglib.mappers.ExternalClassListToModelListMapper;
import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.Beacon;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class BeaconAndroidMapper implements
    ExternalClassListToModelListMapper<Beacon, OrchextraBeacon>,
    ExternalClassToModelMapper<Beacon, OrchextraBeacon> {

  private static final double IMMEDIATE_MAX_DISTANCE_THRESHOLD = 0.5;
  private static final double FAR_MIN_DISTANCE_THRESHOLD = 3.0;

  @Override public List<OrchextraBeacon> externalClassListToModelList(List<Beacon> beacons) {

    List<OrchextraBeacon> beaconList = new ArrayList<>();

    for (Beacon beacon:beacons){
      beaconList.add(externalClassToModel(beacon));
    }
    return beaconList;
  }

  @Override public OrchextraBeacon externalClassToModel(Beacon beacon) {
    return  new OrchextraBeacon(beacon.getId1().toString(), beacon.getId2().toInt(),
        beacon.getId3().toInt(), getDistance(beacon.getDistance()));
  }

  private BeaconDistanceType getDistance(double distance) {
    if (distance < IMMEDIATE_MAX_DISTANCE_THRESHOLD) {
      return BeaconDistanceType.IMMEDIATE;
    }
    else if(distance < FAR_MIN_DISTANCE_THRESHOLD) {
      return BeaconDistanceType.NEAR;
    }
    else {
      return BeaconDistanceType.FAR;
    }
  }

}
