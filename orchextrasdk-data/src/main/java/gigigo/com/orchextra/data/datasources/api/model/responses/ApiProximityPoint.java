package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public abstract class ApiProximityPoint {

  @Expose @SerializedName("id")
  private String id;

  @Expose @SerializedName("code")
  private String code;

  @Expose @SerializedName("name")
  private String name;

  @Expose @SerializedName("tags")
  private List<String> tags;

  @Expose @SerializedName("type")
  private String type;

  @Expose @SerializedName("createdAt")
  private String createdAt;

  @Expose @SerializedName("updatedAt")
  private String updatedAt;

  @Expose @SerializedName("notifyOnExit")
  private boolean notifyOnExit;

  @Expose @SerializedName("notifyOnEntry")
  private boolean notifyOnEntry;

  @Expose @SerializedName("stayTime")
  private int stayTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean getNotifyOnExit() {
    return notifyOnExit;
  }

  public void setNotifyOnExit(boolean notifyOnExit) {
    this.notifyOnExit = notifyOnExit;
  }

  public boolean getNotifyOnEntry() {
    return notifyOnEntry;
  }

  public void setNotifyOnEntry(boolean notifyOnEntry) {
    this.notifyOnEntry = notifyOnEntry;
  }

  public int getStayTime() {
    return stayTime;
  }

  public void setStayTime(int stayTime) {
    this.stayTime = stayTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
