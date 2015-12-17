package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class ApiVuforia {

  @Expose @SerializedName("licenseKey")
  private String licenseKey;

  @Expose @SerializedName("clientAccessKey")
  private String clientAccessKey;

  @Expose @SerializedName("clientSecretKey")
  private String clientSecretKey;

  @Expose @SerializedName("serverAccessKey")
  private String serverAccessKey;

  @Expose @SerializedName("serverSecretKey")
  private String serverSecretKey;

  public String getLicenseKey() {
    return licenseKey;
  }

  public void setLicenseKey(String licenseKey) {
    this.licenseKey = licenseKey;
  }

  public String getClientAccessKey() {
    return clientAccessKey;
  }

  public void setClientAccessKey(String clientAccessKey) {
    this.clientAccessKey = clientAccessKey;
  }

  public String getClientSecretKey() {
    return clientSecretKey;
  }

  public void setClientSecretKey(String clientSecretKey) {
    this.clientSecretKey = clientSecretKey;
  }

  public String getServerAccessKey() {
    return serverAccessKey;
  }

  public void setServerAccessKey(String serverAccessKey) {
    this.serverAccessKey = serverAccessKey;
  }

  public String getServerSecretKey() {
    return serverSecretKey;
  }

  public void setServerSecretKey(String serverSecretKey) {
    this.serverSecretKey = serverSecretKey;
  }
}
