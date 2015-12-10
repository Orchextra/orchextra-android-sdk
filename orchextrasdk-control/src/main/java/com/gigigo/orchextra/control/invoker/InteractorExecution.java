package com.gigigo.orchextra.control.invoker;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import java.util.HashMap;
import java.util.Map;

public class InteractorExecution<T> {
  private InteractorResult<T> interactorResult;
  private final Map<Class<? extends InteractorError>, InteractorResult<? extends InteractorError>>
      errors =
      new HashMap<>(0);
  private final Interactor<InteractorResponse<T>> interactor;
  private int priority;

  public InteractorExecution(Interactor<InteractorResponse<T>> interactor) {
    this.interactor = interactor;
  }

  public InteractorExecution<T> result(InteractorResult<T> interactorResult) {
    this.interactorResult = interactorResult;
    return this;
  }

  public InteractorExecution<T> error(Class<? extends InteractorError> errorClass,
      InteractorResult<? extends InteractorError> interactorError) {
    this.errors.put(errorClass, interactorError);
    return this;
  }

  public InteractorExecution<T> priority(int priority) {
    this.priority = priority;
    return this;
  }

  public Interactor<InteractorResponse<T>> getInteractor() {
    return interactor;
  }

  public InteractorResult<? extends InteractorError> getInteractorErrorResult(
      Class<? extends InteractorError> errorClass) {
    return errors.get(errorClass);
  }

  public InteractorResult<T> getInteractorResult() {
    return interactorResult;
  }

  public void execute(InteractorInvoker interactorInvoker) {
    interactorInvoker.execute(this);
  }

  public int getPriority() {
    return priority;
  }
}
