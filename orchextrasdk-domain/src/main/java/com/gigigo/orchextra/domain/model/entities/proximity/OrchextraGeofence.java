package com.gigigo.orchextra.domain.model.entities.proximity;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class OrchextraGeofence extends ProximityPoint {

  private com.gigigo.orchextra.domain.model.vo.OrchextraPoint point;
  private int radius;

  public com.gigigo.orchextra.domain.model.vo.OrchextraPoint getPoint() {
    return point;
  }

  public void setPoint(com.gigigo.orchextra.domain.model.vo.OrchextraPoint point) {
    this.point = point;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }
}
