package com.gigigo.orchextra.android.beacons;

import com.gigigo.gggjavalib.general.utils.Hashing;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class BeaconRegionsFactory {

  //TODO DELETE class or move to testing purpose

  private static final String BEACONS_UUID = "e6775403-f0dd-40c4-87db-95e755738ad1";
  private static final String BEACON_MAYOR_10 = "10";
  private static final String BEACON_MAYOR_11 = "11";
  private static final String BEACON_ID_CONCAT_CHAR = "_";

  public static void obtainRegionsToScan(RegionsProviderListener regionsProviderListener) {

    List<Region> regions = new ArrayList<>();

    Identifier UUIDidentifier = Identifier.parse(BEACONS_UUID);
    Identifier mayorTenIdentifier = Identifier.parse(BEACON_MAYOR_10);
    Identifier mayorElevenIdentifier = Identifier.parse(BEACON_MAYOR_11);

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

    Region region = new Region(regionTenCodeId, UUIDidentifier, mayorTenIdentifier, null);
    regions.add(region);

    region = new Region(regionElevenCodeId, UUIDidentifier, mayorElevenIdentifier, null);
    regions.add(region);

    regionsProviderListener.onRegionsReady(regions);

  }

}
