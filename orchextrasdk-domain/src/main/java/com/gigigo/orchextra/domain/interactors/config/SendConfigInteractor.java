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
