package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class ApiConfigData {

  @Expose @SerializedName("geoMarketing")
  private List<ApiGeofence> geoMarketing;

  @Expose @SerializedName("proximity")
  private List<ApiBeacon> proximity;

  @Expose @SerializedName("theme")
  private ApiTheme theme;

  @Expose @SerializedName("vuforia")
  private ApiVuforia vuforia;

  @Expose @SerializedName("requestWaitTime")
  private int requestWaitTime;

  public List<ApiGeofence> getGeoMarketing() {
    return geoMarketing;
  }

  public void setGeoMarketing(List<ApiGeofence> geoMarketing) {
    this.geoMarketing = geoMarketing;
  }

  public List<ApiBeacon> getProximity() {
    return proximity;
  }

  public void setProximity(List<ApiBeacon> proximity) {
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
