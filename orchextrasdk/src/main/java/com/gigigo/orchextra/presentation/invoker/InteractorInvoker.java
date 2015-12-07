package com.gigigo.orchextra.presentation.invoker;

import java.util.concurrent.Future;

public interface InteractorInvoker {
  <T> Future<T> execute(InteractorExecution<T> interactor);
}