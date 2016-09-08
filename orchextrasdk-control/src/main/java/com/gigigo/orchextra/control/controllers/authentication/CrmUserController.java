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
import com.gigigo.orchextra.domain.interactors.user.CrmDeviceTagsSyncTask;
import com.gigigo.orchextra.domain.interactors.user.CrmUserTagsSyncTask;
import com.gigigo.orchextra.domain.interactors.user.SaveCrmUserInteractor;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.List;

import orchextra.javax.inject.Provider;


public class CrmUserController {

  private final InteractorInvoker interactorInvoker;
  private final Provider<InteractorExecution> saveCrmUserInteractorExecutionProvider;
  private final ConfigObservable configObservable;
  private final ErrorLogger errorLogger;
  private final CrmUserTagsSyncTask userTagsSyncTask;
  private final CrmDeviceTagsSyncTask deviceTagsSyncTask;


  public CrmUserController(InteractorInvoker interactorInvoker,
                           Provider<InteractorExecution> saveCrmUserInteractorExecutionProvider,
                           ConfigObservable configObservable,
                           ErrorLogger errorLogger,
                           CrmUserTagsSyncTask userTagsSyncTask,
                           CrmDeviceTagsSyncTask deviceTagsSyncTask) {

    super();

    this.interactorInvoker = interactorInvoker;
    this.saveCrmUserInteractorExecutionProvider = saveCrmUserInteractorExecutionProvider;
    this.configObservable = configObservable;
    this.errorLogger = errorLogger;
    this.userTagsSyncTask = userTagsSyncTask;
    this.deviceTagsSyncTask = deviceTagsSyncTask;
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
    return userTagsSyncTask.retrieveCrmUserTags();
  }

  public void setUserTags(List<String> userTagList) {
    userTagsSyncTask.saveUserTags(userTagList);
  }

  public List<String> getUserBusinessUnits() {
    return userTagsSyncTask.retrieveCrmUserBusinessUnits();
  }

  public void setUserBusinessUnits(List<String> userBusinessUnits) {
    userTagsSyncTask.saveUserBusinessUnits(userBusinessUnits);
  }

  public List<CustomField> getUserCustomFields() {
    return userTagsSyncTask.retrieveCrmUserCustomFields();
  }

  public void setUserCustomFields(List<CustomField> customFieldList) {
    userTagsSyncTask.saveUserCustomFields(customFieldList);
  }

  public List<String> getDeviceTags() {
    return deviceTagsSyncTask.retrieveCrmUserTags();
  }

  public void setDeviceTags(List<String> deviceTags) {
    deviceTagsSyncTask.saveDeviceTags(deviceTags);
  }

  public List<String> getDeviceBusinessUnits() {
    return deviceTagsSyncTask.retrieveDeviceBusinessUnits();
  }

  public void setDeviceBusinessUnits(List<String> deviceBusinessUnits) {
    deviceTagsSyncTask.saveDeviceBusinessUnits(deviceBusinessUnits);
  }
}
