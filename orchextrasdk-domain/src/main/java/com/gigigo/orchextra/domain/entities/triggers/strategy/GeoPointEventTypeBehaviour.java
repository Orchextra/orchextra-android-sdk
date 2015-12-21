package com.gigigo.orchextra.domain.entities.triggers.strategy;

import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface GeoPointEventTypeBehaviour extends SupportedBehaviour{
  GeoPointEventType getGeoPointEventType();
}
