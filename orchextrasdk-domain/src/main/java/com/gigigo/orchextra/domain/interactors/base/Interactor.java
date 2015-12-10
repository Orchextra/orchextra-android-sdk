package com.gigigo.orchextra.domain.interactors.base;

import java.util.concurrent.Callable;

public interface Interactor<T extends InteractorResponse> extends Callable<T> {
  @Override T call() throws Exception;
}
