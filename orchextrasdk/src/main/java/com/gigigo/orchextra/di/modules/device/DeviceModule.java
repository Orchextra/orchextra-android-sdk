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

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.permissions.GoogleApiPermissionChecker;
import com.gigigo.orchextra.device.permissions.PermissionCameraImp;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;
import com.gigigo.orchextra.sdk.OrchextraTasksManager;
import com.gigigo.orchextra.sdk.OrchextraTasksManagerImpl;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.sdk.application.ForegroundTasksManagerImpl;

import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;


@Module(includes = {BluetoothModule.class, ActionsModule.class, NotificationsModule.class, GeolocationModule.class, ImageRecognitionModule.class})
public class DeviceModule {

  @Singleton @Provides
  ForegroundTasksManager provideBackgroundTasksManager(OrchextraTasksManager orchextraTasksManager,
      PermissionChecker permissionChecker, ContextProvider contextProvider){
    return new ForegroundTasksManagerImpl(orchextraTasksManager, permissionChecker, contextProvider);
  }

  @Singleton @Provides
  OrchextraTasksManager provideOrchextraTasksManager(BeaconScanner beaconScanner,
      ConfigDelegateImp configDelegateImp, GeofenceRegister geofenceRegister, OrchextraLogger orchextraLogger){
    return  new OrchextraTasksManagerImpl(beaconScanner, configDelegateImp, geofenceRegister, orchextraLogger);
  }

  @Singleton
  @Provides
  GoogleApiPermissionChecker provideGoogleApiPermissionChecker(ContextProvider contextProvider,
                                                               FeatureListener featureListener) {
    return new GoogleApiPermissionChecker(contextProvider.getApplicationContext(), featureListener);
  }

  @Provides PermissionChecker providePermissionChecker(ContextProvider contextProvider) {
    return new AndroidPermissionCheckerImpl(contextProvider.getApplicationContext(), contextProvider);
  }

  @Singleton
  @Provides PermissionLocationImp providePermissionLocationImp() {
    return new PermissionLocationImp();
  }

  @Singleton
  @Provides
  PermissionCameraImp providePermissionCameraImp() {
    return new PermissionCameraImp();
  }

  @Singleton
  @Provides GoogleApiClientConnector provideGoogleApiClientConnector(ContextProvider contextProvider,
                                                                     GoogleApiPermissionChecker googleApiPermissionChecker,
                                                                      OrchextraLogger orchextraLogger) {

    return new GoogleApiClientConnector(contextProvider, googleApiPermissionChecker,
        orchextraLogger);
  }

  @Singleton
  @Provides AndroidApp provideAndroidApp() {
    return new AndroidApp();
  }

  @Singleton
  @Provides AndroidDevice provideAndroidDevice(ContextProvider contextProvider) {
    return new AndroidDevice(contextProvider.getApplicationContext());
  }

  @Singleton
  @Provides ErrorLogger provideErrorLogger(final OrchextraLogger orchextraLogger) {
    return new ErrorLogger() {
      @Override public void log(BusinessError businessError) {
        //TODO SEE WHY ERROR TEXT HERE IS NULL AND WRITE SOMETHING DIFFERENT
        orchextraLogger.log(businessError.getMessage(), OrchextraSDKLogLevel.ERROR);
      }

      @Override public void log(String message) {
        orchextraLogger.log(message, OrchextraSDKLogLevel.ERROR);
      }
    };
  }


}
