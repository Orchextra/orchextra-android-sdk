package com.gigigo.orchextra.domain.invoker;

import com.gigigo.orchextra.domain.interactors.Interactor;
import com.gigigo.orchextra.domain.interactors.InteractorResponse;
import java.util.concurrent.Callable;

public class PriorityInteractorDecorator<T extends InteractorResponse>
    implements Callable<T>, PriorizableInteractor {

  private Interactor<T> interactor;
  private int priority;

  public PriorityInteractorDecorator(Interactor<T> interactor, int priority) {
    this.interactor = interactor;
    this.priority = priority;
  }

  @Override public T call() throws Exception {
    return interactor.call();
  }

  @Override public int getPriority() {
    return priority;
  }

  @Override public String getDescription() {
    return interactor.getClass().toString();
  }
}