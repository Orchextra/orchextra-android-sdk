package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsService;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public class RegionsProviderInteractor implements Interactor<InteractorResponse<List<OrchextraRegion>>> {

  private final ObtainRegionsService obtainRegionsService;

  public RegionsProviderInteractor(ObtainRegionsService obtainRegionsService) {
    this.obtainRegionsService = obtainRegionsService;
  }

  @Override public InteractorResponse<List<OrchextraRegion>> call() throws Exception {
    return obtainRegionsService.obtainRegionsFromLocalStorage();
  }

}
