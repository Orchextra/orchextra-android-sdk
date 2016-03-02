package com.gigigo.orchextra.domain.services.auth.errors;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class AuthenticationError implements InteractorError {

  private final BusinessError businessError;

  public AuthenticationError(BusinessError businessError) {
    this.businessError = businessError;
  }

  @Override public BusinessError getError() {
    return businessError;
  }
}
