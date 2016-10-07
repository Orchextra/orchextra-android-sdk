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

package com.gigigo.orchextra.di.modules.data;

import com.gigigo.orchextra.dataprovision.actions.ActionsDataProviderImpl;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.dataprovision.authentication.AuthenticationDataProviderImpl;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.OrchextraStatusDBDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.dataprovision.config.ConfigDataProviderImpl;
import com.gigigo.orchextra.dataprovision.config.datasource.TriggersConfigurationDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.dataprovision.imagerecognition.ImageRecognitionLocalDataProviderImp;
import com.gigigo.orchextra.dataprovision.proximity.ProximityAndGeofencesLocalDataProviderImp;
import com.gigigo.orchextra.dataprovision.proximity.datasource.ProximityDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.GeofenceDBDataSource;
import com.gigigo.orchextra.dataprovision.status.OrchextraStatusDataProviderImpl;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ImageRecognitionLocalDataProvider;
import com.gigigo.orchextra.domain.dataprovider.OrchextraStatusDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityAndGeofencesLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;

import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;


@Module(includes = DataModule.class)
public class DataProviderModule {

  @Provides @Singleton AuthenticationDataProvider provideAuthenticationDataProvider(
      AuthenticationDataSource authenticationDataSource, SessionDBDataSource sessionDBDataSource){
    return new AuthenticationDataProviderImpl(authenticationDataSource, sessionDBDataSource);
  }

  @Provides @Singleton ConfigDataProvider provideConfigDataProvider(
      ConfigDataSource configDataSource,
      TriggersConfigurationDBDataSource triggersConfigurationDBDataSource,
      SessionDBDataSource sessionDBDataSource,
      Session session){
    return new ConfigDataProviderImpl(configDataSource, triggersConfigurationDBDataSource, sessionDBDataSource, session);
  }

  @Provides @Singleton ActionsDataProvider provideActionsDataProvider(
      ActionsDataSource actionsDataSource){
    return new ActionsDataProviderImpl(actionsDataSource);
  }

    @Provides @Singleton
    ProximityAndGeofencesLocalDataProvider provideGeofenceDataProvider(TriggersConfigurationDBDataSource triggersConfigurationDBDataSource,
                                                                       ProximityDBDataSource proximityDBDataSource,
                                                                       GeofenceDBDataSource geofenceDBDataSource) {
        return new ProximityAndGeofencesLocalDataProviderImp(triggersConfigurationDBDataSource, proximityDBDataSource, geofenceDBDataSource);
    }

  @Provides @Singleton OrchextraStatusDataProvider provideOrchextraStatusDataProvider(
      OrchextraStatusDBDataSource orchextraStatusDBDataSource) {
    return new OrchextraStatusDataProviderImpl(orchextraStatusDBDataSource);
  }

  @Provides @Singleton ImageRecognitionLocalDataProvider provideImageRecognitionLocalDataProvider(
      TriggersConfigurationDBDataSource triggersConfigurationDBDataSource) {
    return new ImageRecognitionLocalDataProviderImp(triggersConfigurationDBDataSource);
  }

}
