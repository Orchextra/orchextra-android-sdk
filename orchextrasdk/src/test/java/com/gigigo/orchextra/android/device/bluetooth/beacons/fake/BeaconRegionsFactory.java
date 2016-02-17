package com.gigigo.orchextra.android.device.bluetooth.beacons.fake;

import com.gigigo.gggjavalib.general.utils.Hashing;
import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class BeaconRegionsFactory {

  private static final String BEACONS_UUID = "e6775403-f0dd-40c4-87db-95e755738ad1";
  private static final int BEACON_MAYOR_10 = 10;
  private static final int BEACON_MAYOR_11 = 1;
  private static final String BEACON_ID_CONCAT_CHAR = "_";

  public static void obtainRegionsToScan(RegionsProviderListener regionsProviderListener) {

    List<OrchextraRegion> regions = new ArrayList<>();

    String regionElevenCodeId ="regionElevenCodeId";
    String regionTenCodeId = "regionTenCodeId";

    try {
      regionElevenCodeId = Hashing.generateMd5(
          BEACONS_UUID + BEACON_ID_CONCAT_CHAR + BEACON_MAYOR_11);
      regionTenCodeId = Hashing.generateMd5(BEACONS_UUID+BEACON_ID_CONCAT_CHAR+BEACON_MAYOR_10);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    OrchextraRegion region = new OrchextraRegion(regionTenCodeId, BEACONS_UUID, BEACON_MAYOR_10, -1, true);
    regions.add(region);

    region = new OrchextraRegion(regionElevenCodeId, BEACONS_UUID, BEACON_MAYOR_11, -1, true);
    regions.add(region);

    regionsProviderListener.onRegionsReady(regions);

  }

}
