package com.gigigo.orchextra.android.proximity.geofencing;

import android.os.Bundle;

import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.android.googleClient.GoogleApiClientConnector;
import com.gigigo.orchextra.android.permissions.PermissionLocationImp;
import com.gigigo.orchextra.android.proximity.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

public class GeofenceDeviceRegister implements ResultCallback<Status> {

    private final ContextProvider contextProvider;
    private final GeofencePendingIntentCreator geofencePendingIntentCreator;
    private final GoogleApiClientConnector googleApiClientConnector;
    private final PermissionChecker permissionChecker;
    private final PermissionLocationImp accessFineLocationPermissionImp;

    private GeofencingRequest geofencingRequest;

    public GeofenceDeviceRegister(ContextProvider contextProvider,
                                  GoogleApiClientConnector googleApiClientConnector,
                                  GeofencePendingIntentCreator geofencePendingIntentCreator,
                                  PermissionChecker permissionChecker,
                                  PermissionLocationImp accessFineLocationPermissionImp) {

        this.contextProvider = contextProvider;
        this.googleApiClientConnector = googleApiClientConnector;
        this.geofencePendingIntentCreator = geofencePendingIntentCreator;
        this.permissionChecker = permissionChecker;
        this.accessFineLocationPermissionImp = accessFineLocationPermissionImp;
    }

    public void register(GeofencingRequest geofencingRequest) {
        this.geofencingRequest = geofencingRequest;

        googleApiClientConnector.setOnConnectedListener(onConnectedListener);
        googleApiClientConnector.connect();
    }

    private void registerGeofencesOnDevice() {
        if (contextProvider.getCurrentActivity() != null) {
            permissionChecker.askForPermission(accessFineLocationPermissionImp, userPermissionResponseListener, contextProvider.getCurrentActivity());
        }
    }

    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            GGGLogImpl.log("Success!", LogLevel.INFO);
        } else if (status.hasResolution()) {
            GGGLogImpl.log("Handle resolution!", LogLevel.INFO);
            // TODO Handle resolution - startResolutionForResult (Activity, int)
        } else if (status.isCanceled()) {
            GGGLogImpl.log("Canceled!", LogLevel.INFO);
        } else if (status.isInterrupted()) {
            GGGLogImpl.log("Interrupted!", LogLevel.INFO);
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
        LocationServices.GeofencingApi.removeGeofences(googleApiClientConnector.getGoogleApiClient(),
                geofencePendingIntentCreator.getGeofencingPendingIntent());

        LocationServices.GeofencingApi.addGeofences(
                googleApiClientConnector.getGoogleApiClient(), geofencingRequest,
                geofencePendingIntentCreator.getGeofencingPendingIntent()
        ).setResultCallback(this);
    }
}
