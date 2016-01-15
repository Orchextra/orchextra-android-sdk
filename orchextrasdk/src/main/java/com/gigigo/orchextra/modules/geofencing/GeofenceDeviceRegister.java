package com.gigigo.orchextra.modules.geofencing;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.utils.googleClient.GoogleApiClientConnector;
import com.gigigo.orchextra.modules.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

public class GeofenceDeviceRegister implements ResultCallback<Status> {

    private final Context context;

    private final GeofencePendingIntentCreator geofencePendingIntentCreator;
    private final GoogleApiClientConnector googleApiClientConnector;

    private GoogleApiClient client;

    private GeofencingRequest geofencingRequest;

    public GeofenceDeviceRegister(Context context,
                                  GoogleApiClientConnector googleApiClientConnector,
                                  GeofencePendingIntentCreator geofencePendingIntentCreator) {
        this.context = context;
        this.googleApiClientConnector = googleApiClientConnector;
        this.geofencePendingIntentCreator = geofencePendingIntentCreator;
    }

    public void register(GeofencingRequest geofencingRequest) {
        this.geofencingRequest = geofencingRequest;

        googleApiClientConnector.setOnConnectedListener(onConnectedListener);
        googleApiClientConnector.initGoogleApiClient();
    }

    private void registerGeofencesOnDevice() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationServices.GeofencingApi.removeGeofences(client, geofencePendingIntentCreator.getGeofencingPendingIntent());

        LocationServices.GeofencingApi.addGeofences(
                client, geofencingRequest,
                geofencePendingIntentCreator.getGeofencingPendingIntent()
        ).setResultCallback(this);
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
    }

    private GoogleApiClientConnector.OnConnectedListener onConnectedListener =
            new GoogleApiClientConnector.OnConnectedListener() {
                @Override
                public void onConnected(Bundle bundle) {
                    registerGeofencesOnDevice();
                }
            };
}
