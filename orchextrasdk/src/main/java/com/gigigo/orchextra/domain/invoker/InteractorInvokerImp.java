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

import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
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