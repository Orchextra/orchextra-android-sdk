package com.gigigo.orchextra.domain.model.triggers.strategy.behaviours;

import com.gigigo.orchextra.domain.model.triggers.strategy.SupportedBehaviour;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface GeoPointBehaviour extends SupportedBehaviour {
  OrchextraPoint getPoint();
}
