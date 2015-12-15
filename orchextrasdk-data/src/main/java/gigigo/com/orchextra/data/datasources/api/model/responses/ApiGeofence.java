package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ApiGeofence {

  @Expose @SerializedName("id")
  private String id;

  @Expose @SerializedName("point")
  private ApiPoint point;

  @Expose @SerializedName("radius")
  private Integer radius;

  @Expose @SerializedName("notifyOnExit")
  private Boolean notifyOnExit;

  @Expose @SerializedName("notifyOnEntry")
  private Boolean notifyOnEntry;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ApiPoint getPoint() {
    return point;
  }

  public void setPoint(ApiPoint point) {
    this.point = point;
  }

  public Integer getRadius() {
    return radius;
  }

  public void setRadius(Integer radius) {
    this.radius = radius;
  }

  public Boolean getNotifyOnExit() {
    return notifyOnExit;
  }

  public void setNotifyOnExit(Boolean notifyOnExit) {
    this.notifyOnExit = notifyOnExit;
  }

  public Boolean getNotifyOnEntry() {
    return notifyOnEntry;
  }

  public void setNotifyOnEntry(Boolean notifyOnEntry) {
    this.notifyOnEntry = notifyOnEntry;
  }
}
