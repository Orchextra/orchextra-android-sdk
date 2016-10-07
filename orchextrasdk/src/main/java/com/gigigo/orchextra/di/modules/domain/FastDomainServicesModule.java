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

import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.OrchextraStatusDataProvider;
import com.gigigo.orchextra.domain.services.status.ClearOrchextraCredentialsDomainService;
import com.gigigo.orchextra.domain.services.status.LoadOrchextraDomainServiceStatus;
import com.gigigo.orchextra.domain.services.status.UpdateOrchextraDomainServiceStatus;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Singleton;

@Module
public class FastDomainServicesModule {

  @Provides @Singleton
  LoadOrchextraDomainServiceStatus provideLoadOrchextraServiceStatus(
      OrchextraStatusDataProvider orchextraStatusDataProvider){
    return new LoadOrchextraDomainServiceStatus(orchextraStatusDataProvider);
  }


  @Provides @Singleton
  UpdateOrchextraDomainServiceStatus provideUpdateOrchextraServiceStatus(
      OrchextraStatusDataProvider orchextraStatusDataProvider){
    return new UpdateOrchextraDomainServiceStatus(orchextraStatusDataProvider);
  }

  @Provides @Singleton
  ClearOrchextraCredentialsDomainService provideClearOrchextraCredentialsService(
          AuthenticationDataProvider authenticationDataProvider) {
    return new ClearOrchextraCredentialsDomainService(authenticationDataProvider);
  }
}
