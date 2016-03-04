package com.gigigo.orchextra.control.controllers;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.status.OrchextraStatusInteractor;
import com.gigigo.orchextra.domain.interactors.status.StatusOperationType;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import javax.inject.Provider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public class OrchextraStatusAccessorAccessorImpl implements OrchextraStatusAccessor {

  private OrchextraStatus orchextraStatus = null;

  private final InteractorInvoker interactorInvoker;
  private final ErrorLogger errorLogger;
  private final Provider<InteractorExecution> orchextraStatusInteractorExecution;

  public OrchextraStatusAccessorAccessorImpl(InteractorInvoker interactorInvoker,
      Provider<InteractorExecution> orchextraStatusInteractorExecution,
      ErrorLogger errorLogger) {
    this.interactorInvoker = interactorInvoker;
    this.orchextraStatusInteractorExecution = orchextraStatusInteractorExecution;
    this.errorLogger = errorLogger;
  }

  @Override public boolean isInitialized() throws NullPointerException{
    return orchextraStatus.isInitialized();
  }

  @Override public boolean isStarted() throws NullPointerException {
    return orchextraStatus.isStarted();
  }

  @Override public Session getSession() {
    return null;
  }

  @Override public Crm getCrm() {
    return null;
  }

  @Override public void loadOrchextraStatus() {
    InteractorExecution interactorExecution = orchextraStatusInteractorExecution.get();
    OrchextraStatusInteractor interactor = (OrchextraStatusInteractor) interactorExecution.getInteractor();
    interactor.loadData(StatusOperationType.LOAD_ORCHEXTRA_STATUS);
    executeBeaconInteractor(interactorExecution);
  }

  @Override public void updateOrchextraStatus(OrchextraStatus orchextraStatus) {
    InteractorExecution interactorExecution = orchextraStatusInteractorExecution.get();
    OrchextraStatusInteractor interactor = (OrchextraStatusInteractor) interactorExecution.getInteractor();
    interactor.loadData(StatusOperationType.UPDATE_ORCHEXTRA_STATUS, orchextraStatus);
    executeBeaconInteractor(interactorExecution);
  }

  private void executeBeaconInteractor(InteractorExecution interactorExecution) {
    interactorExecution.result(new InteractorResult<OrchextraStatus>() {
      @Override public void onResult(OrchextraStatus result) {
        orchextraStatus = result;
      }
    }).error(InteractorError.class, new InteractorResult<InteractorError>() {
      @Override public void onResult(InteractorError result) {
        errorLogger.log(result.getError());
      }
    }).execute(interactorInvoker);
  }
}
