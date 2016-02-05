package com.gigigo.orchextra.android.beacons;

import java.util.List;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public interface RegionsProviderListener {

  void onRegionsReady(List<Region> regions);

}
