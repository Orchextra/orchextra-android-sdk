package com.gigigo.orchextra.domain.services.auth.errors;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class SdkAuthError implements InteractorError {

  private BusinessError error;

  public SdkAuthError(BusinessError error) {
    this.error = error;
  }

  public BusinessError getError() {
    return error;
  }
}
