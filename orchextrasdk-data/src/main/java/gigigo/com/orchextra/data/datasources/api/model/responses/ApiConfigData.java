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

package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class ApiConfigData {

  @Expose @SerializedName("geoMarketing") private List<ApiGeofence> geoMarketing;

  @Expose @SerializedName("proximity") private List<ApiBeaconRegion> proximity;

  @Expose @SerializedName("theme") private ApiTheme theme;

  @Expose @SerializedName("vuforia") private ApiVuforia vuforia;

  @Expose @SerializedName("requestWaitTime") private int requestWaitTime;

  public List<ApiGeofence> getGeoMarketing() {
    return geoMarketing;
  }

  public void setGeoMarketing(List<ApiGeofence> geoMarketing) {
    this.geoMarketing = geoMarketing;
  }

  public List<ApiBeaconRegion> getProximity() {
    return proximity;
  }

  public void setProximity(List<ApiBeaconRegion> proximity) {
    this.proximity = proximity;
  }

  public ApiTheme getTheme() {
    return theme;
  }

  public void setTheme(ApiTheme theme) {
    this.theme = theme;
  }

  public ApiVuforia getVuforia() {
    return vuforia;
  }

  public void setVuforia(ApiVuforia vuforia) {
    this.vuforia = vuforia;
  }

  public int getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(int requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }
}
