package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRealm extends RealmObject {

  private int minor;
  private int major;
  @Required
  private String uuid;
  private boolean active;

  private String id;
  private String code;
  private String name;
  private RealmList<KeyWordRealm> tags;
  private String type;
  private String createdAt;
  private String updatedAt;
  private Boolean notifyOnExit;
  private Boolean notifyOnEntry;
  private Integer stayTime;

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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

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

  public RealmList<KeyWordRealm> getTags() {
    return tags;
  }

  public void setTags(RealmList<KeyWordRealm> tags) {
    this.tags = tags;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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

  public Integer getStayTime() {
    return stayTime;
  }

  public void setStayTime(Integer stayTime) {
    this.stayTime = stayTime;
  }
}
