package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigInfoResult{

  private BeaconList beacons;
  private GeofenceList geofences;
  private VuforiaReady vuforia;
  private SupportsTheme theme;

  private int requestWaitTime;


  public List<Geofence> getGeofences() {
    return geofences.getGeofences();
  }

  public List<Beacon> getBeacons() {
    return beacons.getBeacons();
  }

  public Vuforia getVuforia() {
    return vuforia.getVuforia();
  }

  public Theme getTheme() {
    return theme.getTheme();
  }

  public boolean supportsTheme(){
    return theme.isSupported();
  }

  public boolean supportsVuforia(){
    return vuforia.isSupported();
  }

  public boolean supportsBeacons(){
    return beacons.isSupported();
  }

  public boolean supportsGeofences(){
    return geofences.isSupported();
  }

  public int getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(int requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }

  public void setBeacons(BeaconList beacons) {
    this.beacons = beacons;
  }

  public void setGeofences(GeofenceList geofences) {
    this.geofences = geofences;
  }

  public void setVuforia(VuforiaReady vuforia) {
    this.vuforia = vuforia;
  }

  public void setTheme(SupportsTheme theme) {
    this.theme = theme;
  }
}
