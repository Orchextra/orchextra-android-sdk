package com.gigigo.orchextra.domain.interactors.error;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class GenericError implements InteractorError{

  private BusinessError error;

  public GenericError(BusinessError error) {
    this.error = error;
  }

  public BusinessError getError() {
    return error;
  }

  public void setError(BusinessError error) {
    this.error = error;
  }
}
