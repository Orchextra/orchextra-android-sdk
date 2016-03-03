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

package com.gigigo.orchextra.control.controllers.authentication;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

import javax.inject.Provider;

import me.panavtec.threaddecoratedview.views.ThreadSpec;


public class SaveUserController extends Controller<SaveUserDelegate> {

  private final InteractorInvoker interactorInvoker;
  private final Provider<InteractorExecution> interactorExecutionProvider;
  private final ConfigObservable configObservable;

  public SaveUserController(InteractorInvoker interactorInvoker,
      Provider<InteractorExecution> interactorExecutionProvider, ThreadSpec mainThreadSpec,
      ConfigObservable configObservable) {
    super(mainThreadSpec);
    this.interactorInvoker = interactorInvoker;
    this.interactorExecutionProvider = interactorExecutionProvider;
    this.configObservable = configObservable;
  }

  @Override public void onDelegateAttached() {
  }

  public void saveUser(Crm crm) {
    InteractorExecution interactorExecution = interactorExecutionProvider.get();
    SaveUserInteractor saveUserInteractor =
        (SaveUserInteractor) interactorExecution.getInteractor();
    saveUserInteractor.setCrm(crm);

    interactorExecution.result(new InteractorResult<OrchextraUpdates>() {
      @Override public void onResult(OrchextraUpdates orchextraUpdates) {
        notifyChanges(orchextraUpdates);
      }
    }).execute(interactorInvoker);
  }

  private void notifyChanges(OrchextraUpdates result) {
    if (result.hasChanges()) {
      configObservable.notifyObservers(result);
    }
  }
}
