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

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
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