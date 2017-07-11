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
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraTLMEddyStoneBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.altbeacon.beacon.Beacon;

public class BeaconAndroidMapper
    implements ExternalClassListToModelListMapper<Beacon, OrchextraBeacon>,
    ExternalClassToModelMapper<Beacon, OrchextraBeacon> {

  private static final double IMMEDIATE_MAX_DISTANCE_THRESHOLD = 0.5;
  private static final double FAR_MIN_DISTANCE_THRESHOLD = 3.0;

  @Override public List<OrchextraBeacon> externalClassListToModelList(List<Beacon> beacons) {
    List<OrchextraBeacon> beaconList = new ArrayList<>();
    for (Beacon beacon : beacons) {
      beaconList.add(externalClassToModel(beacon));
    }
    return beaconList;
  }

  public List<OrchextraBeacon> externalClassListToModelList(List<Beacon> beacons,
      HashMap<String, String> eddyStoneUrlFrameMap) {
    List<OrchextraBeacon> beaconList = new ArrayList<>();
    for (Beacon beacon : beacons) {
      String url="";
     if(eddyStoneUrlFrameMap!=null && isEddyStone(beacon)){
       url= eddyStoneUrlFrameMap.get((String)beacon.getBluetoothAddress());
     }
      beaconList.add(externalClassToModel(beacon,url));
    }
    return beaconList;
  }

  @Override public OrchextraBeacon externalClassToModel(Beacon beacon) {
    if (isEddyStone(beacon))  //eddystone
    {
      return createOrchextraEddyStoneBeacon(beacon,"");
    } else {
      return createOrchextraIBeaconBeacon(beacon);
    }
  }

   public OrchextraBeacon externalClassToModel(Beacon beacon,String url) {
    if (isEddyStone(beacon))  //eddystone
    {
      return createOrchextraEddyStoneBeacon(beacon,url);
    } else {
      return createOrchextraIBeaconBeacon(beacon);
    }
  }

  private OrchextraBeacon createOrchextraIBeaconBeacon(Beacon beacon) {
    return new OrchextraBeacon(beacon.getId1().toString(), beacon.getId2().toInt(),
        beacon.getId3().toInt(), getDistance(beacon.getDistance()));
  }

  //region EddyStone
  private boolean isEddyStone(Beacon beacon) {
    return beacon.getServiceUuid() == 0xfeaa;
  }

  private OrchextraBeacon createOrchextraEddyStoneBeacon(Beacon beacon, String url) {
    long telemetryVersion = 0l;
    long batteryMilliVolts = 0l;
    long temperature = 0l;
    long pduCount = 0l;
    long uptime = 0l;
    //parsing data here, from tlm copy that wherever
    if (beacon != null
        && beacon.getExtraDataFields() != null
        && beacon.getExtraDataFields().size() > 0) {
      try {
        telemetryVersion = beacon.getExtraDataFields().get(0);
        batteryMilliVolts = beacon.getExtraDataFields().get(1);
        temperature = beacon.getExtraDataFields().get(2);
        pduCount = beacon.getExtraDataFields().get(3);
        uptime = beacon.getExtraDataFields().get(4);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    OrchextraTLMEddyStoneBeacon tlm = new OrchextraTLMEddyStoneBeacon(uptime, batteryMilliVolts,
        convertToDecimalDegrees(temperature));

    return new OrchextraBeacon(beacon.getId1().toString(), beacon.getId2().toString(),
        getEddyStoneDistance(beacon), tlm, url);
  }

  private BeaconDistanceType getEddyStoneDistance(Beacon beacon) {

    //double distance = calculateDistanceWithRefRSSI0(beacon.getTxPower(), beacon.getRssi());
    double newdistance = calculateDistance(beacon.getTxPower(), beacon.getRssi());
    //fixme check distance with IOS sdk version of that
    return getDistance(newdistance);
  }

  //region Calculate Distance when txpower is from 0 meters
  static final double edd_coefficient1 = 0.42093;
  static final double edd_coefficient2 = 6.9476;
  static final double edd_coefficient3 = 0.54992;

  private double calculateDistanceWithRefRSSI0(int meassureRssiAt0M, double rssi) {
    if (rssi == 0) {
      return -1.0; // if we cannot determine accuracy, return -1.
    }
    meassureRssiAt0M = meassureRssiAt0M - 25; //round to refrrsi 4 1 metro

    double ratio = rssi * 1.0 / meassureRssiAt0M;
    double distance;
    if (ratio < 1.0) {
      distance = Math.pow(ratio, 10);
    } else {
      distance = (edd_coefficient1) * Math.pow(ratio, edd_coefficient2) + edd_coefficient3;
    }
    return distance;
  }

  //region new calculate distance eddystone
  public static double calculateDistance(int txPower, double rssi) {
    if (rssi == 0) {
      return -1.0; // if we cannot determine distance, return -1.
    }

    double ratio = rssi * 1.0 / -41;
    if (ratio < 1.0) {
      return Math.pow(ratio, 10);
    } else {
      double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
      return accuracy;
    }
  }

  //endregion

  //endregion
  //eddystone Degrees
  private float convertToDecimalDegrees(long temperature) {
    String temperatureSTR = "N/S";
    float tempF = 0.0f;
    if (temperature > 0) tempF = temperature / 256f;

    return tempF;
  }
  //endregion

  private BeaconDistanceType getDistance(double distance) {
    if (distance < IMMEDIATE_MAX_DISTANCE_THRESHOLD) {
      return BeaconDistanceType.IMMEDIATE;
    } else if (distance < FAR_MIN_DISTANCE_THRESHOLD) {
      return BeaconDistanceType.NEAR;
    } else {
      return BeaconDistanceType.FAR;
    }
  }
}
