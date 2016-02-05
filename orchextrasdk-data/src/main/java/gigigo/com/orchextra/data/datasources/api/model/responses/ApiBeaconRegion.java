package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ApiBeaconRegion extends ApiProximityPoint{

  @Expose @SerializedName("minor")
  private int minor;

  @Expose @SerializedName("major")
  private int major;

  @Expose @SerializedName("uuid")
  private String uuid;

  @Expose @SerializedName("active")
  private boolean active;

  public int getMinor() {
    return minor;
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  public int getMajor() {
    return major;
  }

  public void setMajor(int major) {
    this.major = major;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
