package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ApiBeacon extends ApiProximityPoint{

  @Expose @SerializedName("minor")
  private Integer minor;

  @Expose @SerializedName("major")
  private Integer major;

  @Expose @SerializedName("uuid")
  private String uuid;

  @Expose @SerializedName("active")
  private Boolean active;

  public Integer getMinor() {
    return minor;
  }

  public void setMinor(Integer minor) {
    this.minor = minor;
  }

  public Integer getMajor() {
    return major;
  }

  public void setMajor(Integer major) {
    this.major = major;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
