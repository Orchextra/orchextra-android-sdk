package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.model.ScheduledActionEvent;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class OrchextraGeofence extends ProximityPoint implements ScheduledActionEvent {

  private OrchextraPoint point;
  private int radius;
  private double distanceToDeviceInKm;
  private String geofenceId;

  private ActionRelated actionRelated;

  public OrchextraPoint getPoint() {
    return point;
  }

  public void setPoint(OrchextraPoint point) {
    this.point = point;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public double getDistanceToDeviceInKm() {
    return distanceToDeviceInKm;
  }

  public void setDistanceToDeviceInKm(double distanceToDeviceInKm) {
    this.distanceToDeviceInKm = distanceToDeviceInKm;
  }

  public void setGeofenceId(String geofenceId) {
    this.geofenceId = geofenceId;
  }

  public String getGeofenceId() {
    return geofenceId;
  }

  @Override public String getActionRelatedId() {
    return actionRelated.getActionId();
  }

  @Override
  public boolean hasActionRelated() {
    return actionRelated != null;
  }

  @Override public void setActionRelated(ActionRelated actionRelated) {
    this.actionRelated = actionRelated;
  }

  @Override public boolean relatedActionIsCancelable() {
    if (actionRelated == null){
      return false;
    }else{
      return actionRelated.isCancelable();
    }
  }

}
