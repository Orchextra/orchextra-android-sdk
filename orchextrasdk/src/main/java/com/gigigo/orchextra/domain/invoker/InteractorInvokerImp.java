package com.gigigo.orchextra.domain.invoker;

import com.gigigo.orchextra.domain.interactors.Interactor;
import com.gigigo.orchextra.domain.interactors.InteractorResponse;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class InteractorInvokerImp implements InteractorInvoker {

  private ExecutorService executor;
  private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

  public InteractorInvokerImp(ExecutorService executor,
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
    this.executor = executor;
    this.uncaughtExceptionHandler = uncaughtExceptionHandler;
  }

  @Override public <T> Future<T> execute(InteractorExecution<T> execution) {
    Interactor<InteractorResponse<T>> interactor = execution.getInteractor();
    if (execution.getInteractorResult() != null) {
      return (Future<T>) executor.submit(new InteractorExecutionFutureTask<>(execution, uncaughtExceptionHandler));
    } else {
      return (Future<T>) executor.submit(new PriorityInteractorDecorator<>(interactor, execution.getPriority()));
    }
  }
}