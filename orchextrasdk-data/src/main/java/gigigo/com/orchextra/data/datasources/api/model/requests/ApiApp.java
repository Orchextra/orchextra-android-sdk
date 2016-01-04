package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ApiApp {

  @Expose @SerializedName("appVersion")
  private String appVersion;

  @Expose  @SerializedName("buildVersion")
  private String buildVersion;

  @Expose @SerializedName("bundleId")
  private String bundleId;

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getBuildVersion() {
    return buildVersion;
  }

  public void setBuildVersion(String buildVersion) {
    this.buildVersion = buildVersion;
  }

  public String getBundleId() {
    return bundleId;
  }

  public void setBundleId(String bundleId) {
    this.bundleId = bundleId;
  }
}
