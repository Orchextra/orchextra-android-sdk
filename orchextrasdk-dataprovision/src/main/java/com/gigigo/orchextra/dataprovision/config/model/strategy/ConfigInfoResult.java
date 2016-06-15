/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;
import java.util.List;


public class ConfigInfoResult {

  private RegionList regions;
  private GeofenceList geofences;
  private VuforiaReady vuforia;
  @Deprecated
  private SupportsTheme theme;

  private int requestWaitTime;

  public List<OrchextraGeofence> getGeofences() {
    return geofences.getGeofences();
  }

  public List<OrchextraRegion> getRegions() {
    return regions.getRegions();
  }

  public Vuforia getVuforia() {
    return vuforia.getVuforia();
  }
  @Deprecated
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
  @Deprecated
  public void setTheme(SupportsTheme theme) {
    this.theme = theme;
  }

  public static class Builder {

    private List<OrchextraGeofence> geoMarketing;
    private List<OrchextraRegion> proximity;
    private Theme theme;
    private int requestWaitTime;
    private Vuforia vuforia;

    public Builder(int requestWaitTime, List<OrchextraGeofence> geoMarketing,
        List<OrchextraRegion> proximity, Theme theme,
        Vuforia vuforia) {

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
      configInfoResult.setTheme(new RealSupportsThemeImpl(theme));
      configInfoResult.setVuforia(new VuforiaReadyImpl(vuforia));

      return configInfoResult;
    }
  }
}
