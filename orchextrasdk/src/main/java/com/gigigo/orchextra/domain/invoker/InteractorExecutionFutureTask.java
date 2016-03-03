/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.invoker;


import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class InteractorExecutionFutureTask<T> extends FutureTask<T>
    implements PriorizableInteractor {

  private final InteractorExecution<T> interactorExecution;
  private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
  private final String description;

  public InteractorExecutionFutureTask(InteractorExecution<T> interactorExecution,
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
    super((Callable<T>) interactorExecution.getInteractor());
    this.interactorExecution = interactorExecution;
    this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    this.description = interactorExecution.getInteractor().getClass().toString();
  }

  @Override protected void done() {
    super.done();
    try {
      handleResponse((InteractorResponse<T>) get());
    } catch (Exception e) {
      handleException(e);
    }
  }

  private void handleException(Exception e) {
    Throwable causeException = e.getCause();
    unhandledException(causeException != null ? causeException : e);
  }

  private void handleResponse(InteractorResponse<T> response) {
    if (response.hasError()) {
      handleError(response.getError());
    } else {
      handleResult(response.getResult());
    }
  }

  private void handleResult(T result) {
    interactorExecution.getInteractorResult().onResult(result);
  }

  private void handleError(InteractorError error) {
    InteractorResult errorResult =
        interactorExecution.getInteractorErrorResult(error.getClass());
    if (errorResult != null) {
      errorResult.onResult(error);
    } else {
      unhandledException(new RuntimeException("Unhandled handleError action: " + error.getClass().toString()));
    }
  }

  private void unhandledException(Throwable cause) {
    UnhandledInteractorException e =
        new UnhandledInteractorException(description, cause.getClass().getName());
    e.initCause(cause);
    uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e);
  }

  public int getPriority() {
    return interactorExecution.getPriority();
  }

  @Override public String getDescription() {
    return description;
  }
}