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

package com.gigigo.orchextra.sdk;

import android.app.Application;
import android.content.Context;

import com.gigigo.orchextra.control.controllers.authentication.SaveUserController;
import com.gigigo.orchextra.di.components.DaggerOrchextraComponent;
import com.gigigo.orchextra.di.components.OrchextraComponent;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.sdk.model.OrcUserToCrmConverter;

import javax.inject.Inject;


public class OrchextraManager {

  private static OrchextraManager instance;
  private InjectorImpl injector;

  @Inject
  OrchextraActivityLifecycle orchextraActivityLifecycle;

  @Inject
  Session session;

  @Inject
  OrcUserToCrmConverter orcUserToCrmConverter;

  @Inject
  SaveUserController saveUserController;

  private void initDependencyInjection(Context applicationContext, OrchextraManagerCompletionCallback orchextraCompletionCallback) {
    OrchextraComponent orchextraComponent = DaggerOrchextraComponent.builder()
        .orchextraModule(new OrchextraModule(applicationContext, orchextraCompletionCallback))
        .build();
    injector = new InjectorImpl(orchextraComponent);
    orchextraComponent.injectOrchextra(OrchextraManager.instance);
  }

  public static synchronized void sdkInitialize(Application application, String apiKey,
      String apiSecret, OrchextraManagerCompletionCallback orchextraCompletionCallback) {
    OrchextraManager.instance = new OrchextraManager();
    OrchextraManager.instance.init(application, orchextraCompletionCallback, apiKey, apiSecret);
  }

  private void init(Application application, OrchextraManagerCompletionCallback orchextraCompletionCallback,
      String apiKey, String apiSecret) {

    initDependencyInjection(application.getApplicationContext(), orchextraCompletionCallback);

    session.setAppParams(apiKey, apiSecret);

    initLifecyle(application);

  }

  private void initLifecyle(Application application) {
    application.registerActivityLifecycleCallbacks(orchextraActivityLifecycle);
  }

  public static synchronized void setCustomSchemeReceiver(CustomOrchextraSchemeReceiver customSchemeReceiver){
    OrchextraComponent orchextraComponent = getInjector().getOrchextraComponent();
    OrchextraModule orchextraModule = orchextraComponent.getOrchextraModule();
    orchextraModule.setCustomSchemeReceiver(customSchemeReceiver);
  }

  public static synchronized void setUser(ORCUser user) {
    OrcUserToCrmConverter orcUserToCrmConverter = OrchextraManager.instance.orcUserToCrmConverter;
    SaveUserController saveUserController = OrchextraManager.instance.saveUserController;

    Crm crm = orcUserToCrmConverter.convertOrcUserToCrm(user);
    saveUserController.saveUser(crm);
  }

  public static InjectorImpl getInjector() {
    return OrchextraManager.instance.injector;
  }

}
