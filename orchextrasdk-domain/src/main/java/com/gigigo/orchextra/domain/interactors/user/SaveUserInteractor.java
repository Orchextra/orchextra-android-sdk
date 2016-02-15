package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.services.config.ConfigService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class SaveUserInteractor implements Interactor<InteractorResponse<ClientAuthData>> {

  private final AuthenticationService authenticationService;
  private final ConfigService configService;
  private String crmId;

  public SaveUserInteractor(AuthenticationService authenticationService,
      ConfigService configService) {

    this.authenticationService = authenticationService;
    this.configService = configService;

  }

  public void setCrm(String crmId) {
    this.crmId = crmId;
  }

  @Override public InteractorResponse<ClientAuthData> call() {

    InteractorResponse<ClientAuthData> reponse = authenticationService.authenticateUserWithCrmId(crmId);

    if (!reponse.hasError()){
      configService.refreshConfig();
    }

    return reponse;
  }
}
