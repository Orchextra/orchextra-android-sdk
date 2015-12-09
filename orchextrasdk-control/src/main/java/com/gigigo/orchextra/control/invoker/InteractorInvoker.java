package com.gigigo.orchextra.control.invoker;

import java.util.concurrent.Future;

public interface InteractorInvoker {
  <T> Future<T> execute(InteractorExecution<T> interactor);
}