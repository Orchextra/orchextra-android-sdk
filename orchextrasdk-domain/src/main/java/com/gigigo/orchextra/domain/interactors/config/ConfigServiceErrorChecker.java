package com.gigigo.orchextra.domain.interactors.config;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public class ConfigServiceErrorChecker extends ServiceErrorChecker {

  public ConfigServiceErrorChecker(AuthenticationService authenticationService) {
    super(authenticationService);
  }

  @Override protected InteractorResponse checkConcreteException(BusinessError businessError) {
    return new InteractorResponse(new GenericError(businessError));
  }

  @Override protected InteractorResponse checkConcreteBusinessErrors(BusinessError businessError) {
    return new InteractorResponse(new GenericError(businessError));
  }
}
