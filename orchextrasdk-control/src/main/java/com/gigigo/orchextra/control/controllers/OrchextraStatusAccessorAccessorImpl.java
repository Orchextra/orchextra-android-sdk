package com.gigigo.orchextra.control.controllers;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.status.OrchextraStatusInteractor;
import com.gigigo.orchextra.domain.interactors.status.StatusOperationType;
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
    loadOrchextraStatus();
    return orchextraStatus.isInitialized();
  }

  @Override public boolean isStarted() throws NullPointerException {
    loadOrchextraStatus();
    return orchextraStatus.isStarted();
  }

  @Override public void started() {
    loadOrchextraStatus();
    orchextraStatus.setStarted(true);
    updateOrchextraStatus();
  }

  @Override public void stopped() {
    loadOrchextraStatus();
    orchextraStatus.setStarted(false);
    updateOrchextraStatus();
  }

  @Override public void initialize() {
    loadOrchextraStatus();
    orchextraStatus.setInitialized(true);
    updateOrchextraStatus();
  }

  private void loadOrchextraStatus() {
    if (this.orchextraStatus==null){
      FutureResult<OrchextraStatus> futureOrchextraStatus = new FutureResult();
      startAsyncOperation(StatusOperationType.LOAD_ORCHEXTRA_STATUS, futureOrchextraStatus);
      this.orchextraStatus = getOrchextraStatus(futureOrchextraStatus);
    }
  }

  private void updateOrchextraStatus() {
    FutureResult<OrchextraStatus> futureOrchextraStatus = new FutureResult();
    startAsyncOperation(StatusOperationType.UPDATE_ORCHEXTRA_STATUS, futureOrchextraStatus);
    this.orchextraStatus = getOrchextraStatus(futureOrchextraStatus);
  }


  private void startAsyncOperation(StatusOperationType statusOperationType,
      FutureResult futureOrchextraStatus) {
    InteractorExecution interactorExecution = orchextraStatusInteractorExecution.get();
    OrchextraStatusInteractor interactor = (OrchextraStatusInteractor) interactorExecution.getInteractor();

    if (statusOperationType == StatusOperationType.LOAD_ORCHEXTRA_STATUS){
      interactor.loadData();
    }else{
      interactor.updateData(orchextraStatus);
    }

    executeBeaconInteractor(interactorExecution, futureOrchextraStatus);
  }


  private OrchextraStatus getOrchextraStatus(FutureResult<OrchextraStatus> future) {
    try {
      return future.get();
    } catch (Exception e) {
      //TODO
      return null;
    }
  }

  private void executeBeaconInteractor(InteractorExecution interactorExecution, final FutureResult futureOrchextraStatus) {
    interactorExecution.result(new InteractorResult<OrchextraStatus>() {
      @Override public void onResult(OrchextraStatus result) {
        orchextraStatus = result;
        futureOrchextraStatus.onReadyFutureResult(orchextraStatus);
      }
    }).error(InteractorError.class, new InteractorResult<InteractorError>() {
      @Override public void onResult(InteractorError result) {
        errorLogger.log(result.getError());
        futureOrchextraStatus.onErrorFutureResult(result.getError().getMessage());
      }
    }).execute(interactorInvoker);
  }
}
