package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigInfoResult {

  private RegionList regions;
  private GeofenceList geofences;
  private VuforiaReady vuforia;
  private SupportsTheme theme;

  private int requestWaitTime;

  public List<OrchextraGeofence> getGeofences() {
    return geofences.getGeofences();
  }

  public List<OrchextraRegion> getRegions() {
    return regions.getRegions();
  }

  public com.gigigo.orchextra.domain.model.entities.Vuforia getVuforia() {
    return vuforia.getVuforia();
  }

  public Theme getTheme() {
    return theme.getTheme();
  }

  public boolean supportsTheme() {
    return theme.isSupported();
  }

  public boolean supportsVuforia() {
    return vuforia.isSupported();
  }

  public boolean supportsBeacons() {
    return regions.isSupported();
  }

  public boolean supportsGeofences() {
    return geofences.isSupported();
  }

  public int getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(int requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }

  public void setRegions(RegionList regions) {
    this.regions = regions;
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

    private List<OrchextraGeofence> geoMarketing;
    private List<OrchextraRegion> proximity;
    private Theme theme;
    private int requestWaitTime;
    private com.gigigo.orchextra.domain.model.entities.Vuforia vuforia;

    public Builder(int requestWaitTime, List<OrchextraGeofence> geoMarketing,
        List<OrchextraRegion> proximity, Theme theme,
        com.gigigo.orchextra.domain.model.entities.Vuforia vuforia) {

      this.geoMarketing = geoMarketing;
      this.proximity = proximity;
      this.theme = theme;
      this.requestWaitTime = requestWaitTime;
      this.vuforia = vuforia;
    }

    public ConfigInfoResult build() {

      ConfigInfoResult configInfoResult = new ConfigInfoResult();

      configInfoResult.setRequestWaitTime(requestWaitTime);
      configInfoResult.setRegions(new RealRegionListImpl(proximity));
      configInfoResult.setGeofences(new RealGeofenceListImpl(geoMarketing));
      configInfoResult.setTheme(
          new com.gigigo.orchextra.dataprovision.config.model.strategy.RealSupportsThemeImpl(
              theme));
      configInfoResult.setVuforia(new VuforiaReadyImpl(vuforia));

      return configInfoResult;
    }
  }
}
