package com.gigigo.orchextra.domain.model.triggers.strategy.behaviours;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeoDistanceBehaviourImpl implements GeoDistanceBehaviour {

  private double distance = 0.0;

  public GeoDistanceBehaviourImpl(double distance) {
    this.distance = distance;
  }

  @Override public double getGeoDistance() {
    return distance;
  }

  @Override public boolean isSupported() {
    return (distance == 0.0) ? false : true;
  }
}
