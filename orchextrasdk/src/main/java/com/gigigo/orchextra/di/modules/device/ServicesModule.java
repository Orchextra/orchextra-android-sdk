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

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceIntentServiceHandler;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.di.scopes.PerService;
import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.sdk.OrchextraTasksManager;
import com.gigigo.orchextra.sdk.application.BackgroundTasksManagerImpl;

import dagger.Module;
import dagger.Provides;


@Module
public class ServicesModule {

  @PerService @Provides BackgroundTasksManager provideBackgroundTasksManager(
      OrchextraTasksManager orchextraTasksManager,
      PermissionChecker permissionChecker,
      OrchextraStatusAccessor orchextraStatusAccessor){

    return new BackgroundTasksManagerImpl(orchextraTasksManager, permissionChecker,
        orchextraStatusAccessor);
  }

  @PerService
  @Provides
  LocationMapper provideLocationMapper() {
    return new LocationMapper();
  }

  @PerService @Provides
  AndroidGeofenceIntentServiceHandler provideAndroidGeofenceIntentServiceHandler(LocationMapper locationMapper) {
    return new AndroidGeofenceIntentServiceHandler(locationMapper);
  }

}
