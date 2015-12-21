package com.gigigo.orchextra.domain.entities.triggers.strategy;

import com.gigigo.orchextra.domain.entities.Point;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeoPointBehaviourImpl implements GeoPointBehaviour {

  private Point point;

  public GeoPointBehaviourImpl(Point point) {
    this.point = point;
  }

  @Override public Point getPoint() {
    return point;
  }

  @Override public boolean isSupported() {
    return (point==null)? false : true;
  }
}
