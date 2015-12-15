package gigigo.com.orchextra.data.datasources.api.model.resquests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class OrchextraApiConfigRequest {

  @Expose @SerializedName("app")
  private ApiApp app;

  @Expose @SerializedName("device")
  private ApiDevice device;

  @Expose @SerializedName("geoLocation")
  private ApiGeoLocation geoLocation;

  @Expose @SerializedName("notificationPush")
  private ApiNotificationPush notificationPush;

  @Expose @SerializedName("crm")
  private ApiCrm crm;

  public ApiApp getApp() {
    return app;
  }

  public void setApp(ApiApp app) {
    this.app = app;
  }

  public ApiDevice getDevice() {
    return device;
  }

  public void setDevice(ApiDevice device) {
    this.device = device;
  }

  public ApiGeoLocation getGeoLocation() {
    return geoLocation;
  }

  public void setGeoLocation(ApiGeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }

  public ApiNotificationPush getNotificationPush() {
    return notificationPush;
  }

  public void setNotificationPush(ApiNotificationPush notificationPush) {
    this.notificationPush = notificationPush;
  }

  public ApiCrm getCrm() {
    return crm;
  }

  public void setCrm(ApiCrm crm) {
    this.crm = crm;
  }
}
