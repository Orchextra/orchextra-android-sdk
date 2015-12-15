package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ApiBeacon {

  @Expose @SerializedName("id")
  private String id;

  @Expose @SerializedName("minor")
  private Integer minor;

  @Expose @SerializedName("major")
  private Integer major;

  @Expose @SerializedName("uuid")
  private String uuid;

  @Expose @SerializedName("notifyOnEntry")
  private Boolean notifyOnEntry;

  @Expose @SerializedName("notifyOnExit")
  private Boolean notifyOnExit;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public Boolean getNotifyOnEntry() {
    return notifyOnEntry;
  }

  public void setNotifyOnEntry(Boolean notifyOnEntry) {
    this.notifyOnEntry = notifyOnEntry;
  }

  public Boolean getNotifyOnExit() {
    return notifyOnExit;
  }

  public void setNotifyOnExit(Boolean notifyOnExit) {
    this.notifyOnExit = notifyOnExit;
  }
}
