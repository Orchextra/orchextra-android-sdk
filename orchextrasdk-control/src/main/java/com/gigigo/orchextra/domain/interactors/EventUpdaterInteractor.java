package com.gigigo.orchextra.domain.interactors;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/2/16.
 */
public class EventUpdaterInteractor implements Interactor<InteractorResponse<OrchextraRegion>> {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  private OrchextraRegion orchextraRegion;

  public EventUpdaterInteractor(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  @Override public InteractorResponse<OrchextraRegion> call() throws Exception {

    BusinessObject<OrchextraRegion> bo = proximityLocalDataProvider.updateRegionWithActionId(orchextraRegion);

    if (bo.isSuccess()) {
      return new InteractorResponse(bo.getData());
    } else {
      return new InteractorResponse(false);
    }
  }

  public void setOrchextraRegion(OrchextraRegion orchextraRegion) {
    this.orchextraRegion = orchextraRegion;
  }
}