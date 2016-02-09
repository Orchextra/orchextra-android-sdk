package com.gigigo.orchextra.domain.model.config.strategy;

import com.gigigo.orchextra.domain.model.MethodSupported;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public interface RegionList extends MethodSupported {
  List<OrchextraRegion> getRegions();

  boolean hasChanged();
}
