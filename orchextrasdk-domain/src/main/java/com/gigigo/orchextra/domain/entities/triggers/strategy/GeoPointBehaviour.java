package com.gigigo.orchextra.domain.entities.triggers.strategy;

import com.gigigo.orchextra.domain.entities.OrchextraPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface GeoPointBehaviour extends SupportedBehaviour{
  OrchextraPoint getPoint();
}
