package com.gigigo.orchextra.domain.interactors.authentication.errors;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class SdkAuthError implements InteractorError{

  private BusinessError error;

  public SdkAuthError(BusinessError error) {
    this.error = error;
  }

  public BusinessError getError() {
    return error;
  }

  public void setError(BusinessError error) {
    this.error = error;
  }
}
