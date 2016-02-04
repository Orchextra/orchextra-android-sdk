package com.gigigo.orchextra.initalization.features;

import com.gigigo.orchextra.domain.entities.triggers.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public abstract class Feature{

  private FeatureType featureType;
  private StringValueEnum status;

  public Feature(FeatureType featureType, StringValueEnum status) {
    this.featureType = featureType;
    this.status = status;
  }

  public void setStatus(StringValueEnum status) {
    this.status = status;
  }

  public StringValueEnum getStatus() {
    return status;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Feature feature = (Feature) o;

    return featureType == feature.featureType;
  }

  @Override public int hashCode() {
    return featureType != null ? featureType.hashCode() : 0;
  }

  public abstract boolean isSuccess();

  @Override public String toString() {
    return "Feature --> " + featureType.getStringValue() +
        " :: " + status.getStringValue() ;
  }
}
