package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventType;
import com.gigigo.orchextra.domain.model.ScheduledActionEvent;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class OrchextraRegion extends ProximityPoint implements ScheduledActionEvent {

  private final String code;

  private final String uuid;
  private final int minor;
  private final int major;
  private RegionEventType regionEvent;

  private ActionRelated actionRelated;

  private final boolean active;

  public OrchextraRegion(String code, String uuid, int major, int minor, boolean active) {
    this.code = code;
    this.uuid = uuid;
    this.minor = minor;
    this.major = major;
    this.active = active;
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

  public boolean isEnter(){
    return regionEvent == RegionEventType.ENTER;
  }

  public boolean isExit(){
    return regionEvent == RegionEventType.EXIT;
  }

  public RegionEventType getRegionEvent() {
    return regionEvent;
  }

  public void setRegionEvent(RegionEventType regionEvent) {
    this.regionEvent = regionEvent;
  }

  public void setRegionEvent(BeaconEventType eventType) {
    if (eventType == BeaconEventType.REGION_ENTER){
      regionEvent = RegionEventType.ENTER;
    }else{
      regionEvent = RegionEventType.EXIT;
    }
  }

  @Override
  public void setActionRelated(ActionRelated actionRelated) {
    this.actionRelated = actionRelated;
  }

  @Override public String getActionRelatedId() {
    return this.actionRelated.getActionId();
  }

  @Override
  public boolean hasActionRelated() {
    return (actionRelated!=null);
  }

  @Override public boolean relatedActionIsCancelable() {
    return actionRelated.isCancelable();
  }

}
