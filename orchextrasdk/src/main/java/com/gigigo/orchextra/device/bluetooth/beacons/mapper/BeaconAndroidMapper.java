/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.device.bluetooth.beacons.mapper;

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
