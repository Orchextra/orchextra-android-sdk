package com.gigigo.orchextra.domain.entities.triggers.strategy;

import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeoPointEventTypeBehaviourImpl implements GeoPointEventTypeBehaviour {

  private GeoPointEventType geoPointEventType;

  public GeoPointEventTypeBehaviourImpl(GeoPointEventType geoPointEventType) {
    this.geoPointEventType = geoPointEventType;
  }

  @Override public GeoPointEventType getGeoPointEventType() {
    return geoPointEventType;
  }

  @Override public boolean isSupported() {
    return (geoPointEventType==null)? false : true;
  }
}
