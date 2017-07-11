package com.gigigo.orchextra.device.bluetooth.beacons.model;

import java.util.HashMap;
import java.util.List;
import org.altbeacon.beacon.Beacon;

/**
 * Created by nubor on 27/06/2017.
 */

public class BeaconsWrapper {
//we need this class for keep alt beacon list beacons + all urls of eddystone,
// we only need this for go from Beaconscanner to sendAction, we need this for the mapper to convert into OrchextraBeacon
  private List<Beacon> mBeaconList;
  private HashMap<String, String> mEddyStoneUrlFrameMap;

  public List<Beacon> getBeaconList() {
    return mBeaconList;
  }

  public void setBeaconList(List<Beacon> mBeaconList) {
    this.mBeaconList = mBeaconList;
  }

  public HashMap<String, String> getmEddyStoneUrlFrameMap() {
    return mEddyStoneUrlFrameMap;
  }

  public void setmEddyStoneUrlFrameMap(HashMap<String, String> mEddyStoneUrlFrameMap) {
    this.mEddyStoneUrlFrameMap = mEddyStoneUrlFrameMap;
  }
}
