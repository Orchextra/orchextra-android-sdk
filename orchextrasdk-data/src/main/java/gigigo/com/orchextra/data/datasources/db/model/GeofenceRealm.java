package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeofenceRealm extends RealmObject {

  @PrimaryKey
  private String code;
  private RealmPoint point;
  private int radius;
  private boolean notifyOnExit;
  private boolean notifyOnEntry;
  private int stayTime;

  private String id;
  private String name;
  private RealmList<KeyWordRealm> tags;
  private String type;
  private String createdAt;
  private String updatedAt;

  public RealmPoint getPoint() {
    return point;
  }

  public void setPoint(RealmPoint point) {
    this.point = point;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
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
}
