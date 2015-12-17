package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;
import com.gigigo.orchextra.domain.entities.config.strategy.RealBeaconListImpl;
import com.gigigo.orchextra.domain.entities.config.strategy.RealGeofenceListImpl;
import com.gigigo.orchextra.domain.entities.config.strategy.RealSupportsThemeImpl;
import com.gigigo.orchextra.domain.entities.config.strategy.VuforiaReadyImpl;
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
  private Integer requestWaitTime;

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

  public Integer getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(Integer requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }

  public static class ConfigInfoResultBuilder {

    private List<Geofence> geoMarketing;
    private List<Beacon> proximity;
    private Theme theme;
    private int requestWaitTime;
    private Vuforia vuforia;

    public ConfigInfoResultBuilder(List<Geofence> geoMarketing, List<Beacon> proximity,
        Theme theme, int requestWaitTime, Vuforia vuforia) {

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
