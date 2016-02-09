package com.gigigo.orchextra.domain.model.entities.proximity;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class OrchextraRegion extends ProximityPoint {

  private final String code;

  private final String uuid;
  private final int minor;
  private final int major;

  private String actionRelated;

  private final boolean active;

  public OrchextraRegion(String code, String uuid, int major, int minor, boolean active) {
    this.code = code;
    this.uuid = uuid;
    this.minor = minor;
    this.major = major;
    this.active = active;
    this.actionRelated = "";
  }

  public String getCode() {
    return code;
  }

  public String getUuid() {
    return uuid;
  }

  public int getMinor() {
    return minor;
  }

  public int getMajor() {
    return major;
  }

  public boolean isActive() {
    return active;
  }

  public String getActionRelated() {
    return actionRelated;
  }

  public void setActionRelated(String actionRelated) {
    this.actionRelated = actionRelated;
  }

  public boolean hasActionRelated() {
    return (actionRelated!=null) && (actionRelated.length()>0);
  }
}
