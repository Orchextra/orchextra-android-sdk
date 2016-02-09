package com.gigigo.orchextra.domain.model.triggers.strategy.behaviours;

import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.SupportedBehaviour;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface GeoPointEventTypeBehaviour extends SupportedBehaviour {
  GeoPointEventType getGeoPointEventType();
}
