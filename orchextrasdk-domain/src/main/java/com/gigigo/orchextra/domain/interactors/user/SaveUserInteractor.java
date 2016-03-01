package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.config.ConfigService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class SaveUserInteractor implements Interactor<InteractorResponse<OrchextraUpdates>> {

  private final AuthenticationService authenticationService;
  private final ConfigService configService;

  private Crm crm;

  public SaveUserInteractor(AuthenticationService authenticationService,
                            ConfigService configService) {

    this.authenticationService = authenticationService;
    this.configService = configService;
  }

  public void setCrm(Crm crm) {
    this.crm = crm;
  }

  @Override public InteractorResponse<OrchextraUpdates> call() {
    authenticationService.saveUser(crm);

    InteractorResponse<OrchextraUpdates> boOrchextraUpdates = configService.refreshConfig();

    return boOrchextraUpdates;
  }
}
