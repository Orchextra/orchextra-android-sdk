package com.gigigo.orchextra.domain.entities;

import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigInfoResult {

  private List<Geofence> geoMarketing;
  private List<Beacon> proximity;
  private Theme theme;
  private String requestWaitTime;
  private Vuforia vuforia;

  public List<Geofence> getGeoMarketing() {
    return geoMarketing;
  }

  public void setGeoMarketing(List<Geofence> geoMarketing) {
    this.geoMarketing = geoMarketing;
  }

  public List<Beacon> getProximity() {
    return proximity;
  }

  public void setProximity(List<Beacon> proximity) {
    this.proximity = proximity;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  public String getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(String requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }

  public Vuforia getVuforia() {
    return vuforia;
  }

  public void setVuforia(Vuforia vuforia) {
    this.vuforia = vuforia;
  }
}
