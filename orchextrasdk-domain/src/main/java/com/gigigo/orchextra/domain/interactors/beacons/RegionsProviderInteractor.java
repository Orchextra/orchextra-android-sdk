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

package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsService;
import java.util.List;

public class RegionsProviderInteractor
    implements Interactor<InteractorResponse<List<OrchextraRegion>>> {

  private final ObtainRegionsService obtainRegionsService;

  public RegionsProviderInteractor(ObtainRegionsService obtainRegionsService) {
    this.obtainRegionsService = obtainRegionsService;
  }

  @Override public InteractorResponse<List<OrchextraRegion>> call() throws Exception {
    return obtainRegionsService.obtainRegionsFromLocalStorage();
  }
}
