package com.gigigo.orchextra.domain.entities;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class OrchextraBeacon {

  private String id;
  private final String uuid;
  private final int mayor;
  private final int minor;
  private final BeaconDistance beaconDistance;
  private long timeStampt;

  public OrchextraBeacon(String uuid, int mayor, int minor, BeaconDistance beaconDistance) {
    this.uuid = uuid;
    this.mayor = mayor;
    this.minor = minor;
    this.beaconDistance = beaconDistance;
  }

  public void setTimeStampt(long timeStampt) {
    this.timeStampt = timeStampt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
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

  public BeaconDistance getBeaconDistance() {
    return beaconDistance;
  }

  public long getTimeStampt() {
    return timeStampt;
  }


}
