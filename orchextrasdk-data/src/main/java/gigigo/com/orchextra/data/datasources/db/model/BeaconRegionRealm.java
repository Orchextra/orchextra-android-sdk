package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRegionRealm extends RealmObject {

  public static final String CODE_FIELD_NAME = "code";

  @PrimaryKey
  @Required private String code;

  private String uuid;
  private int minor;
  private int major;
  private String eventType;
  private String actionRelated;
  private boolean active;
  private boolean actionRelatedCancelable;

  public BeaconRegionRealm(BeaconRegionRealm beaconRegionRealm) {
    this.code = beaconRegionRealm.getCode();
    this.uuid = beaconRegionRealm.getUuid();
    this.major = beaconRegionRealm.getMajor();
    this.minor = beaconRegionRealm.getMinor();
    this.eventType = beaconRegionRealm.getEventType();
    this.actionRelated = beaconRegionRealm.getActionRelated();
    this.actionRelatedCancelable = beaconRegionRealm.isActionRelatedCancelable();
    this.active = beaconRegionRealm.isActive();
  }

  public BeaconRegionRealm() {}

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

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

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getActionRelated() {
    return actionRelated;
  }

  public void setActionRelated(String actionRelated) {
    this.actionRelated = actionRelated;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isActionRelatedCancelable() {
    return actionRelatedCancelable;
  }

  public void setActionRelatedCancelable(boolean actionRelatedCancelable) {
    this.actionRelatedCancelable = actionRelatedCancelable;
  }
}