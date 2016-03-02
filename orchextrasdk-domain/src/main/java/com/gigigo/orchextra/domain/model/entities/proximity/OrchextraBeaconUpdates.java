package com.gigigo.orchextra.domain.model.entities.proximity;

import java.util.List;

public class OrchextraBeaconUpdates {

  List<OrchextraRegion> newRegions;
  List<OrchextraRegion> deleteRegions;

  public OrchextraBeaconUpdates(List<OrchextraRegion> newRegions,
      List<OrchextraRegion> deleteRegions) {
    setNewRegions(newRegions);
    setDeleteRegions(deleteRegions);
  }

  public List<OrchextraRegion> getNewRegions() {
    return newRegions;
  }

  public void setNewRegions(List<OrchextraRegion> newRegions) {
    this.newRegions = newRegions;
  }

  public List<OrchextraRegion> getDeleteRegions() {
    return deleteRegions;
  }

  public void setDeleteRegions(List<OrchextraRegion> deleteRegions) {
    this.deleteRegions = deleteRegions;
  }

  public boolean hasChanges() {
    return newRegions.size() > 0 || deleteRegions.size() > 0;
  }
}
