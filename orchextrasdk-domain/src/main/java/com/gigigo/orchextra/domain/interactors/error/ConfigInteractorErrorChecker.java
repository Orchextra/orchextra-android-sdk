package com.gigigo.orchextra.domain.interactors.error;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public class ConfigInteractorErrorChecker extends InteractorErrorChecker{

  public ConfigInteractorErrorChecker(AuthErrorHandler authErrorHandler, PendingInteractorExecution pendingInteractorExecution) {
    super(authErrorHandler, pendingInteractorExecution);
  }

  @Override protected InteractorError checkConcreteException(BusinessError businessError) {
    return null;
  }

  @Override protected InteractorError checkConcreteBusinessErrors(BusinessError businessError) {
    return null;
  }

}
