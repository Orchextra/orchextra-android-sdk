package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconBusinessErrorType;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconsInteractorError;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.DomaninService;

public class RegionCheckerService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public RegionCheckerService(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public InteractorResponse obtainCheckedRegion(OrchextraRegion orchextraRegion) {
    if (orchextraRegion.isEnter()) {
      return storeRegion(orchextraRegion);
    } else {
      return deleteStoredRegion(orchextraRegion);
    }
  }

  private InteractorResponse deleteStoredRegion(OrchextraRegion orchextraRegion) {
    BusinessObject<OrchextraRegion> bo = proximityLocalDataProvider.deleteRegion(orchextraRegion);
    if (!bo.isSuccess()) {
      return new InteractorResponse(
          new BeaconsInteractorError(BeaconBusinessErrorType.NO_SUCH_REGION_IN_ENTER));
    } else {
      return new InteractorResponse(bo.getData());
    }
  }

  private InteractorResponse storeRegion(OrchextraRegion orchextraRegion) {
    BusinessObject<OrchextraRegion> bo = proximityLocalDataProvider.obtainRegion(orchextraRegion);
    if (bo.isSuccess()) {
      return new InteractorResponse(
          new BeaconsInteractorError(BeaconBusinessErrorType.ALREADY_IN_ENTER_REGION));
    } else {
      bo = proximityLocalDataProvider.storeRegion(orchextraRegion);
      return new InteractorResponse(bo.getData());
    }
  }
}
