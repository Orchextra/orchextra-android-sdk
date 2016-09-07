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
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.interactors.config.ValidationError;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.interactors.user.RetrieveCrmDeviceTagsSyncTask;
import com.gigigo.orchextra.domain.interactors.user.RetrieveCrmUserTagsSyncTask;
import com.gigigo.orchextra.domain.interactors.user.SaveCrmDeviceTagsInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveCrmUserInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveCrmUserTagsInteractor;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

import java.util.List;

import orchextra.javax.inject.Provider;


public class CrmUserController {

  private final InteractorInvoker interactorInvoker;
  private final Provider<InteractorExecution> saveCrmUserInteractorExecutionProvider;
  private final ConfigObservable configObservable;
  private final ErrorLogger errorLogger;
  private final RetrieveCrmUserTagsSyncTask retrieveUserTagsSyncTask;
  private final RetrieveCrmDeviceTagsSyncTask retrieveDeviceTagsSyncTask;
  private final SaveCrmUserTagsInteractor saveCrmUserTagsInteractor;
  private final SaveCrmDeviceTagsInteractor saveCrmDeviceTagsInteractor;


  public CrmUserController(InteractorInvoker interactorInvoker,
                           Provider<InteractorExecution> saveCrmUserInteractorExecutionProvider,
                           ConfigObservable configObservable,
                           ErrorLogger errorLogger,
                           RetrieveCrmUserTagsSyncTask retrieveUserTagsSyncTask,
                           RetrieveCrmDeviceTagsSyncTask retrieveDeviceTagsSyncTask,
                           SaveCrmUserTagsInteractor saveCrmUserTagsInteractor,
                           SaveCrmDeviceTagsInteractor saveCrmDeviceTagsInteractor) {

    super();

    this.interactorInvoker = interactorInvoker;
    this.saveCrmUserInteractorExecutionProvider = saveCrmUserInteractorExecutionProvider;
    this.configObservable = configObservable;
    this.errorLogger = errorLogger;
    this.retrieveUserTagsSyncTask = retrieveUserTagsSyncTask;
    this.retrieveDeviceTagsSyncTask = retrieveDeviceTagsSyncTask;
    this.saveCrmUserTagsInteractor = saveCrmUserTagsInteractor;
    this.saveCrmDeviceTagsInteractor = saveCrmDeviceTagsInteractor;
  }

  public void saveUserAndReloadConfig(CrmUser crmUser) {
    InteractorExecution interactorExecution = saveCrmUserInteractorExecutionProvider.get();
    SaveCrmUserInteractor saveCrmUserInteractor =
        (SaveCrmUserInteractor) interactorExecution.getInteractor();
    saveCrmUserInteractor.setCrmUser(crmUser);
    saveCrmUserInteractor.setHasReloadConfig(true);

    interactorExecution.result(new InteractorResult<OrchextraUpdates>() {
      @Override public void onResult(OrchextraUpdates orchextraUpdates) {
        notifyChanges(orchextraUpdates);
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

  public void saveUserOnly(CrmUser crmUser) {
    InteractorExecution interactorExecution = saveCrmUserInteractorExecutionProvider.get();
    SaveCrmUserInteractor saveCrmUserInteractor =
            (SaveCrmUserInteractor) interactorExecution.getInteractor();
    saveCrmUserInteractor.setCrmUser(crmUser);
    saveCrmUserInteractor.setHasReloadConfig(false);

    interactorExecution.error(ValidationError.class, new InteractorResult<ValidationError>() {
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

  public List<String> getUserTags() {
    return retrieveUserTagsSyncTask.retrieveCrmUserTags();
  }

  public void setUserTags(List<String> userTagList) {
    saveCrmUserTagsInteractor.setCrmUserTags(userTagList);
    new InteractorExecution<>(saveCrmUserTagsInteractor).execute(interactorInvoker);
  }

  public List<String> getDeviceTags() {
    return retrieveDeviceTagsSyncTask.retrieveCrmUserTags();
  }

  public void setDeviceTags(List<String> deviceTags) {
    saveCrmDeviceTagsInteractor.setCrmDeviceTags(deviceTags);
    new InteractorExecution<>(saveCrmDeviceTagsInteractor).execute(interactorInvoker);
  }
}
