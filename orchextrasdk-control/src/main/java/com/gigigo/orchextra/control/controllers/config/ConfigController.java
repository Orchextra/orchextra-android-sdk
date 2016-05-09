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

package com.gigigo.orchextra.control.controllers.config;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.interactors.config.ValidationError;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import orchextra.javax.inject.Provider;

public class ConfigController {

  private final InteractorInvoker interactorInvoker;
  private final Provider<InteractorExecution> sendConfigInteractorExecution;
  private final ConfigObservable configObservable;
  private final ErrorLogger errorLogger;

  public ConfigController(InteractorInvoker interactorInvoker,
      Provider<InteractorExecution> sendConfigInteractorExecution,
      ConfigObservable configObservable, ErrorLogger errorLogger) {

    this.interactorInvoker = interactorInvoker;

    this.sendConfigInteractorExecution = sendConfigInteractorExecution;
    this.configObservable = configObservable;
    this.errorLogger = errorLogger;
  }

  public void sendConfiguration() {
    sendConfigInteractorExecution.get().result(new InteractorResult<OrchextraUpdates>() {
      @Override public void onResult(OrchextraUpdates result) {
        if (result != null) {
          notifyChanges(result);
        }
      }
    }).error(ValidationError.class, new InteractorResult<ValidationError>() {
      @Override public void onResult(ValidationError result) {
        manageInteractorError(result);
      }
    }).error(GenericError.class, new InteractorResult<GenericError>() {
      @Override public void onResult(GenericError result) {
        manageInteractorError(result);
      }
    }).execute(interactorInvoker);
  }

  private void manageInteractorError(GenericError result) {
    errorLogger.log(result.getError());
  }

  private void manageInteractorError(ValidationError result) {
    errorLogger.log(result.getError());
    errorLogger.log(result.getValidationErrorDescriptionString());
  }

  private void notifyChanges(OrchextraUpdates result) {
    if (result.hasChanges()) {
      configObservable.notifyObservers(result);
    }
  }
}
