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

package com.gigigo.orchextra.domain.interactors.config;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.services.config.ConfigService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class SendConfigInteractor implements Interactor<InteractorResponse<OrchextraUpdates>> {

  private final ConfigService configService;

  public SendConfigInteractor(ConfigService configService) {
    this.configService = configService;
  }

  @Override public InteractorResponse<OrchextraUpdates> call() throws Exception {
    return configService.refreshConfig();
  }
}
