package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public class ObtainRegionsInteractor implements
    Interactor<InteractorResponse<List<OrchextraRegion>>> {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public ObtainRegionsInteractor(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  @Override
  public InteractorResponse<List<OrchextraRegion>> call() throws Exception {

    BusinessObject<List<OrchextraRegion>> bo = proximityLocalDataProvider.getBeaconRegionsForScan();

    if (bo.isSuccess()){
      return new InteractorResponse<>(bo.getData());
    }else{
      return new InteractorResponse<>(Collections.<OrchextraRegion>emptyList());
    }
  }
}
