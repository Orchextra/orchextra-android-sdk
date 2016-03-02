package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.auth.errors.AuthenticationError;
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
    InteractorResponse<OrchextraUpdates> boOrchextraUpdates;

    BusinessObject<Crm> crmBusinessObject = authenticationService.saveUser(crm);

    if (crmBusinessObject.isSuccess()) {
      boOrchextraUpdates = configService.refreshConfig();
    } else {
      return new InteractorResponse<OrchextraUpdates>(
          new AuthenticationError(crmBusinessObject.getBusinessError()));
    }

    return boOrchextraUpdates;
  }
}
