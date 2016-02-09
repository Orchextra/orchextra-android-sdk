package com.gigigo.orchextra.domain.interactors.error;

import com.gigigo.gggjavalib.business.model.BusinessContentType;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public abstract class InteractorErrorChecker {

  private final AuthErrorHandler authErrorHandler;
  private Interactor interactor;
  private final PendingInteractorExecution pendingInteractorExecution;

  protected InteractorErrorChecker(AuthErrorHandler authErrorHandler,
      PendingInteractorExecution pendingInteractorExecution) {
    this.authErrorHandler = authErrorHandler;
    this.pendingInteractorExecution = pendingInteractorExecution;
  }

  public InteractorError checkErrors(BusinessError businessError){

    if (businessError.getBusinesContentType() == BusinessContentType.BUSINESS_ERROR_CONTENT){
      //BusinessError management
      checkBusinessErrors(businessError);
      return checkConcreteBusinessErrors(businessError);
    }else{
      //Exception management BusinessContentType.EXCEPTION_CONTENT
      checkExceptions(businessError);
      return checkConcreteException(businessError);
    }
  }

  public Interactor getInteractor() {
    return interactor;
  }

  public void setInteractor(Interactor interactor) {
    this.interactor = interactor;
  }

  private void checkExceptions(BusinessError businessError) {

  }

  private void checkBusinessErrors(BusinessError businessError) {
    OrchextraBusinessErrors error = OrchextraBusinessErrors.getEnumTypeFromInt(
        businessError.getCode());

    try {

      switch (error) {
        case NO_AUTH_CREDENTIALS:

          break;

        case NO_AUTH_EXPIRED:
          pendingInteractorExecution.loadInteractor(interactor);
          authErrorHandler.authenticateWhenError(pendingInteractorExecution);
          break;

        default:
          break;
      }
    }catch (Exception e){
      //Log errors
    }
  }

  protected abstract InteractorError checkConcreteException(BusinessError businessError);
  protected abstract InteractorError checkConcreteBusinessErrors(BusinessError businessError);
}
