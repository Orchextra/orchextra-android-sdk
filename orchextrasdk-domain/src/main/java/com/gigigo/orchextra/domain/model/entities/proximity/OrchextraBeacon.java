package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.gggjavalib.general.utils.Hashing;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class OrchextraBeacon {

  private static final String BEACON_CODE_CONCAT_CHAR = "_";

  private String code;
  private final String uuid;
  private final int mayor;
  private final int minor;
  private final BeaconDistanceType beaconDistance;

  public OrchextraBeacon(String uuid, int mayor, int minor, BeaconDistanceType beaconDistance) {
    this.uuid = uuid;
    this.mayor = mayor;
    this.minor = minor;
    this.beaconDistance = beaconDistance;
    try {
      this.code = Hashing.generateMd5((uuid + BEACON_CODE_CONCAT_CHAR + String.valueOf(mayor)
          + BEACON_CODE_CONCAT_CHAR + String.valueOf(minor)).toUpperCase());
    }catch (Exception e){
      this.code = e.getLocalizedMessage();
    }
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUuid() {
    return uuid;
  }

  public int getMayor() {
    return mayor;
  }

  public int getMinor() {
    return minor;
  }

  public BeaconDistanceType getBeaconDistance() {
    return beaconDistance;
  }

}
