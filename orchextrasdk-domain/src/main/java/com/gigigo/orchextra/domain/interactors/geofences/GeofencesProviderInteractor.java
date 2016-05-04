/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.services.proximity.ObtainGeofencesService;
import java.util.List;

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
