package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.MethodSupported;
import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public interface RegionList extends MethodSupported {
  List<OrchextraRegion> getRegions();

  boolean hasChanged();
}
