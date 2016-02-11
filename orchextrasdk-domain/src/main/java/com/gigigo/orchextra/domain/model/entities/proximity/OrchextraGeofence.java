package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class OrchextraGeofence extends ProximityPoint {

  private OrchextraPoint point;
  private int radius;

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
}
