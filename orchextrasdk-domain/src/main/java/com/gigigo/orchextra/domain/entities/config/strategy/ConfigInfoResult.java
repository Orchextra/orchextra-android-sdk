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

  public static class Builder {

    private List<Geofence> geoMarketing;
    private List<Beacon> proximity;
    private Theme theme;
    private int requestWaitTime;
    private Vuforia vuforia;

    public Builder(int requestWaitTime, List<Geofence> geoMarketing, List<Beacon> proximity,
                   Theme theme, Vuforia vuforia) {

      this.geoMarketing = geoMarketing;
      this.proximity = proximity;
      this.theme = theme;
      this.requestWaitTime = requestWaitTime;
      this.vuforia = vuforia;
    }

    public ConfigInfoResult build() {

      ConfigInfoResult configInfoResult = new ConfigInfoResult();

      configInfoResult.setRequestWaitTime(requestWaitTime);
      configInfoResult.setBeacons(new RealBeaconListImpl(proximity));
      configInfoResult.setGeofences(new RealGeofenceListImpl(geoMarketing));
      configInfoResult.setTheme(new RealSupportsThemeImpl(theme));
      configInfoResult.setVuforia(new VuforiaReadyImpl(vuforia));

      return configInfoResult;
    }
  }
}
