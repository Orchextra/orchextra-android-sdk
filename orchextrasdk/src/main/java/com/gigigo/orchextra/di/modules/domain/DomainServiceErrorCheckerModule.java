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

package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.di.qualifiers.ActionsErrorChecker;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.di.scopes.PerExecutionOx;
import com.gigigo.orchextra.domain.interactors.config.ConfigServiceErrorChecker;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;

import com.gigigo.orchextra.domain.services.actions.ActionServiceErrorChecker;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;

import com.gigigo.orchextra.sdk.OrchextraManager;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;


@Module
public class DomainServiceErrorCheckerModule {

  @ConfigErrorChecker @Provides @PerExecutionOx ServiceErrorChecker provideConfigServiceErrorChecker(
      AuthenticationService authenticationService){

    return new ConfigServiceErrorChecker(authenticationService,OrchextraManager.getOrchextraCompletionCallback());
  }


  @ActionsErrorChecker @Provides @PerExecutionOx ServiceErrorChecker provideActionServiceErrorChecker(
      AuthenticationService authenticationService){
    return new ActionServiceErrorChecker(authenticationService,OrchextraManager.getOrchextraCompletionCallback());
  }

}
