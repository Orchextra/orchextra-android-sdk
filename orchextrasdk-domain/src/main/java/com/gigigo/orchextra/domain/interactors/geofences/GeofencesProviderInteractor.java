package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.services.proximity.ObtainGeofencesService;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/4/16.
 */
public class GeofencesProviderInteractor implements
    Interactor<InteractorResponse<List<OrchextraGeofence>>> {

  private final ObtainGeofencesService obtainGeofencesService;

  public GeofencesProviderInteractor(ObtainGeofencesService obtainGeofencesService) {
    this.obtainGeofencesService = obtainGeofencesService;
  }

  @Override public InteractorResponse<List<OrchextraGeofence>> call() throws Exception {
    return obtainGeofencesService.obtainGeofencesFromLocalStorage();
  }
}
