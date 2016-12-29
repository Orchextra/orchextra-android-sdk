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

import com.gigigo.orchextra.delegates.ConfigDelegateImpl;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;

public class OrchextraTasksManagerImpl implements OrchextraTasksManager {

    private static final int FOREGROUND = 1;
    private static final int BACKGROUND = 2;

    private final BeaconScanner beaconScanner;
    private final ConfigDelegateImpl configDelegateImpl;
    private final GeofenceRegister geofenceRegister;
    private final OrchextraLogger orchextraLogger;

    public OrchextraTasksManagerImpl(
            BeaconScanner beaconScanner,
            ConfigDelegateImpl configDelegateImpl,
            GeofenceRegister geofenceRegister,
            OrchextraLogger orchextraLogger) {

        this.beaconScanner = beaconScanner;
        this.configDelegateImpl = configDelegateImpl;
        this.geofenceRegister = geofenceRegister;
        this.orchextraLogger = orchextraLogger;

    }

    @Override
    public void initBackgroundTasks() {
        initTasks(BACKGROUND);
    }

    @Override
    public void reStartBackgroundTasks() {
        orchextraLogger.log("Generic tasks have been ReStarted: Monitoring and Geofences");
        try {
            geofenceRegister.startGeofenceRegister();
            beaconScanner.startMonitoring();
          //  beaconScanner.initAvailableRegionsRangingScanner();
        } catch (Throwable tr) {
          //stupid error Infinite Ranging Scan in Background Mode is not allowed 4 initAgain
            orchextraLogger.log("Something Wrong when Orchextra restarted, geofences, Beacons.", OrchextraSDKLogLevel.ERROR);
        }
    }

    @Override
    public void pauseBackgroundTasks() {
        orchextraLogger.log("Generic tasks will be pause: Monitoring and Geofences");
        try {
            beaconScanner.stopMonitoring();
            beaconScanner.stopRangingScanner();
            geofenceRegister.stopGeofenceRegister();
        } catch (Throwable tr) {
            orchextraLogger.log("Something Wrong when Orchextra pause Task, geofences, Beacons.", OrchextraSDKLogLevel.ERROR);
        }
    }


    @Override
    public void initForegroundTasks() {
        initTasks(FOREGROUND);
    }

    private void initTasks(int appRunningMode) {
        orchextraLogger.log("Generic tasks have been started: Monitoring and Geofences");

        beaconScanner.startMonitoring();
        geofenceRegister.startGeofenceRegister();

        if (appRunningMode == FOREGROUND) {
            orchextraLogger.log("Foreground tasks have been started: Ranging and Request config");
            beaconScanner.initAvailableRegionsRangingScanner();
            configDelegateImpl.sendConfiguration();
        }
    }

    @Override
    public void stopAllTasks() {
        beaconScanner.stopMonitoring();
        beaconScanner.stopRangingScanner();

        geofenceRegister.stopGeofenceRegister();
        geofenceRegister.clearGeofences();

        configDelegateImpl.clearLocalStorage();
    }

    @Override
    public void stopBackgroundServices() {
//background Services have been already stopped at AppStatusEventsListenerImpl.onBackgroundEnd()
        //fix me appstatuslistener must call this method, not like now do
    }

    @Override
    public void stopForegroundTasks() {
        beaconScanner.stopRangingScanner();
    }

    @Override
    public void initBootTasks() {
        configDelegateImpl.sendConfiguration();
        geofenceRegister.registerAllDbGeofences();
    }

}
