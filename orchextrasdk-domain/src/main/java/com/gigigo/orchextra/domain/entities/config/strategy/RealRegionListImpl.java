package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class RealRegionListImpl implements RegionList {

  private List<OrchextraRegion> currentRegions;

  public RealRegionListImpl(List<OrchextraRegion> regions) {
    this.currentRegions = regions;
  }

  @Override public List<OrchextraRegion> getRegions() {
    return currentRegions;
  }

  @Override public boolean hasChanged() {
    return true;
  }

  @Override public boolean isSupported() {
    return (currentRegions ==null)? false : true;
  }
}
