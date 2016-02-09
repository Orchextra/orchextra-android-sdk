package com.gigigo.orchextra.domain.model.triggers.strategy.behaviours;

import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeoPointBehaviourImpl implements GeoPointBehaviour {

  private OrchextraPoint point;

  public GeoPointBehaviourImpl(OrchextraPoint point) {
    this.point = point;
  }

  @Override public OrchextraPoint getPoint() {
    return point;
  }

  @Override public boolean isSupported() {
    return (point==null)? false : true;
  }
}
