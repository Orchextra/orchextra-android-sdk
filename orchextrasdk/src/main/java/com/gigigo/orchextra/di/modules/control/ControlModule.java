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

package com.gigigo.orchextra.di.modules.control;

import com.gigigo.orchextra.control.controllers.status.OrchextraStatusAccessorAccessorImpl;
import com.gigigo.orchextra.control.controllers.authentication.SaveUserController;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.di.modules.domain.DomainModule;
import com.gigigo.orchextra.di.modules.domain.DomainServicesModule;
import com.gigigo.orchextra.di.modules.domain.FastDomainServicesModule;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.di.qualifiers.ConfigInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.GeofenceInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.MainThread;
import com.gigigo.orchextra.di.qualifiers.SaveUserInteractorExecution;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;
import com.gigigo.orchextra.domain.outputs.MainThreadSpec;

import com.gigigo.orchextra.domain.services.status.LoadOrchextraServiceStatus;
import com.gigigo.orchextra.domain.services.status.UpdateOrchextraServiceStatus;
import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

@Module(includes = {DomainModule.class, FastDomainServicesModule.class})
public class ControlModule {

  @Provides @Singleton
  GeofenceController provideProximityItemController(InteractorInvoker interactorInvoker,
      @GeofenceInteractorExecution Provider<InteractorExecution> geofenceInteractorExecution,
      ActionDispatcher actionDispatcher, ErrorLogger errorLogger, @MainThread ThreadSpec mainThreadSpec) {

    return new GeofenceController(interactorInvoker, geofenceInteractorExecution, actionDispatcher,
        errorLogger, mainThreadSpec);
  }

  @Provides
  @Singleton ConfigObservable providesConfigObservable(){
    return new ConfigObservable();
  }

  @Provides
  @Singleton ConfigController provideConfigController(
      InteractorInvoker interactorInvoker,
      @ConfigInteractorExecution Provider<InteractorExecution> sendConfigInteractorProvider,
      ConfigObservable configObservable) {
    return new ConfigController(interactorInvoker, sendConfigInteractorProvider, configObservable);
  }

  @Provides @Singleton
  SaveUserController provideAuthenticationController(
      InteractorInvoker interactorInvoker,
      @SaveUserInteractorExecution Provider<InteractorExecution> interactorExecutionProvider,
      @MainThread ThreadSpec backThreadSpec, ConfigObservable configObservable){

    return new SaveUserController(interactorInvoker, interactorExecutionProvider, backThreadSpec, configObservable);
  }

  @Provides @Singleton OrchextraStatusAccessor provideOrchextraStatusAccessor(
      Session session, LoadOrchextraServiceStatus loadOrchextraServiceStatus,
      UpdateOrchextraServiceStatus updateOrchextraServiceStatus,
      ErrorLogger errorLogger){

    return new OrchextraStatusAccessorAccessorImpl(session, loadOrchextraServiceStatus,
        updateOrchextraServiceStatus, errorLogger);
  }

  @Singleton @Provides @MainThread ThreadSpec provideMainThread(){
    return new MainThreadSpec();
  }
  @Singleton @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }


}
