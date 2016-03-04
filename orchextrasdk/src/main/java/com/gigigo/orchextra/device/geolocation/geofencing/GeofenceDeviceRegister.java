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

package com.gigigo.orchextra.device.geolocation.geofencing;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class GeofenceDeviceRegister implements ResultCallback<Status> {

    private final ContextProvider contextProvider;
    private final GeofencePendingIntentCreator geofencePendingIntentCreator;
    private final GoogleApiClientConnector googleApiClientConnector;
    private final PermissionChecker permissionChecker;
    private final PermissionLocationImp accessFineLocationPermissionImp;
    private final AndroidGeofenceConverter androidGeofenceConverter;

    private OrchextraGeofenceUpdates geofenceUpdates;

    public GeofenceDeviceRegister(ContextProvider contextProvider,
                                  GoogleApiClientConnector googleApiClientConnector,
                                  GeofencePendingIntentCreator geofencePendingIntentCreator,
                                  PermissionChecker permissionChecker,
                                  PermissionLocationImp accessFineLocationPermissionImp,
                                  AndroidGeofenceConverter androidGeofenceConverter) {

        this.contextProvider = contextProvider;
        this.googleApiClientConnector = googleApiClientConnector;
        this.geofencePendingIntentCreator = geofencePendingIntentCreator;
        this.permissionChecker = permissionChecker;
        this.accessFineLocationPermissionImp = accessFineLocationPermissionImp;
        this.androidGeofenceConverter = androidGeofenceConverter;
    }

    public void register(OrchextraGeofenceUpdates geofenceUpdates) {
        this.geofenceUpdates = geofenceUpdates;

        googleApiClientConnector.setOnConnectedListener(onConnectedListener);
        googleApiClientConnector.connect();
    }

    private void registerGeofencesOnDevice() {
        boolean isGranted = permissionChecker.isGranted(accessFineLocationPermissionImp);
        if (isGranted) {
            registerGeofence();
        } else {
            if (contextProvider.getCurrentActivity() != null) {
                permissionChecker.askForPermission(accessFineLocationPermissionImp, userPermissionResponseListener, contextProvider.getCurrentActivity());
            }
        }
    }

    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            GGGLogImpl.log("Registered Geofences Success!", LogLevel.INFO);
        } else if (status.hasResolution()) {
            Activity currentActivity = contextProvider.getCurrentActivity();
            if (currentActivity != null) {
                try {
                    status.startResolutionForResult(currentActivity, status.getStatusCode());
                } catch (IntentSender.SendIntentException e) {
                    GGGLogImpl.log("Geofences Handle resolution!", LogLevel.INFO);
                }
            }
        } else if (status.isCanceled()) {
            GGGLogImpl.log("Registered Geofences Canceled!", LogLevel.INFO);
        } else if (status.isInterrupted()) {
            GGGLogImpl.log("Registered Geofences Interrupted!", LogLevel.INFO);
        }

        if (googleApiClientConnector.isConnected()) {
            googleApiClientConnector.disconnected();
        }
    }

    private GoogleApiClientConnector.OnConnectedListener onConnectedListener =
            new GoogleApiClientConnector.OnConnectedListener() {
                @Override
                public void onConnected(Bundle bundle) {
                    registerGeofencesOnDevice();
                }

                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    GGGLogImpl.log("No se ha podido conectar GoogleApiClientConnector en las peticion de las geofences");
                }
            };

    private UserPermissionRequestResponseListener userPermissionResponseListener = new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed) {
            if (permissionAllowed) {
                registerGeofence();
            }
        }
    };

    @SuppressWarnings("ResourceType")
    private void registerGeofence() {
        GGGLogImpl.log("Registrando geofences...");

        List<String> deleteCodeList = androidGeofenceConverter.getCodeList(geofenceUpdates.getDeleteGeofences());

        if (deleteCodeList.size() > 0) {
            LocationServices.GeofencingApi.removeGeofences(googleApiClientConnector.getGoogleApiClient(), deleteCodeList);
        }

        if (geofenceUpdates.getNewGeofences().size() > 0) {
            GeofencingRequest geofencingRequest = androidGeofenceConverter.convertGeofencesToGeofencingRequest(geofenceUpdates.getNewGeofences());

            LocationServices.GeofencingApi.addGeofences(
                    googleApiClientConnector.getGoogleApiClient(), geofencingRequest,
                    geofencePendingIntentCreator.getGeofencingPendingIntent()
            ).setResultCallback(this);
        }
    }
}
