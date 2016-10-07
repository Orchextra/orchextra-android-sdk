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
import com.gigigo.orchextra.delegates.ConfigDelegateImpl;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.GoogleApiClientConnectorImpl;
import com.gigigo.orchextra.device.information.AndroidSdkVersionAppInfo;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.information.AndroidInstanceIdProvider;
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

    @Singleton
    @Provides
    ForegroundTasksManager provideBackgroundTasksManager(OrchextraTasksManager orchextraTasksManager,
                                                         PermissionChecker permissionChecker, ContextProvider contextProvider) {
        return new ForegroundTasksManagerImpl(orchextraTasksManager, permissionChecker, contextProvider);
    }

    @Singleton
    @Provides
    OrchextraTasksManager provideOrchextraTasksManager(BeaconScanner beaconScanner,
                                                       ConfigDelegateImpl configDelegateImpl, GeofenceRegister geofenceRegister, OrchextraLogger orchextraLogger) {
        return new OrchextraTasksManagerImpl(beaconScanner, configDelegateImpl, geofenceRegister, orchextraLogger);
    }

    @Singleton
    @Provides
    GoogleApiPermissionChecker provideGoogleApiPermissionChecker(ContextProvider contextProvider,
                                                                 FeatureListener featureListener) {
        return new GoogleApiPermissionChecker(contextProvider.getApplicationContext(), featureListener);
    }

    @Provides
    PermissionChecker providePermissionChecker(ContextProvider contextProvider) {
        return new AndroidPermissionCheckerImpl(contextProvider.getApplicationContext(), contextProvider);
    }

    @Singleton
    @Provides
    PermissionLocationImp providePermissionLocationImp() {
        return new PermissionLocationImp();
    }

    @Singleton
    @Provides
    PermissionCameraImp providePermissionCameraImp() {
        return new PermissionCameraImp();
    }

    @Singleton
    @Provides
    GoogleApiClientConnector provideGoogleApiClientConnector(ContextProvider contextProvider,
                                                             GoogleApiPermissionChecker googleApiPermissionChecker,
                                                             OrchextraLogger orchextraLogger) {

        return new GoogleApiClientConnectorImpl(contextProvider, googleApiPermissionChecker,
                orchextraLogger);
    }

    @Singleton
    @Provides
    AndroidSdkVersionAppInfo provideAndroidApp() {
        return new AndroidSdkVersionAppInfo();
    }

    @Singleton
    @Provides
    AndroidDevice provideAndroidDevice(ContextProvider contextProvider,
                                       AndroidInstanceIdProvider androidInstanceIdProvider) {
        return new AndroidDevice(contextProvider.getApplicationContext(), androidInstanceIdProvider);
    }

    @Singleton
    @Provides
    ErrorLogger provideErrorLogger(final OrchextraLogger orchextraLogger) {
        return new ErrorLogger() {
            @Override
            public void log(BusinessError businessError) {
                //When we change the credentials the server say {"status":false,"error":{"code":401}}
                // instead {"status":false,"error":{"message":"Error.","code":403}}
                //no error messages come from server
                if (businessError.getMessage() != null)
                    orchextraLogger.log(businessError.getMessage(), OrchextraSDKLogLevel.ERROR);
                else
                    orchextraLogger.log("Error: " + businessError.getCode(), OrchextraSDKLogLevel.ERROR);

            }

            @Override
            public void log(String message) {
                orchextraLogger.log(message, OrchextraSDKLogLevel.ERROR);
            }
        };
    }


}
