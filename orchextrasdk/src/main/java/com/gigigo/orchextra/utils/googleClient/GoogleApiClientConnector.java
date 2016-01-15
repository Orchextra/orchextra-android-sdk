package com.gigigo.orchextra.utils.googleClient;

import android.content.Context;
import android.os.Bundle;

import com.gigigo.ggglogger.GGGLogImpl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GoogleApiClientConnector implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final Context context;

    private GoogleApiClient client;
    private OnConnectedListener onConnectedListener;

    public GoogleApiClientConnector(Context context) {
        this.context = context;
    }

    public void initGoogleApiClient() {
        client = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        GGGLogImpl.log("onConnected");

        if (onConnectedListener != null) {
            onConnectedListener.onConnected(bundle);
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        GGGLogImpl.log("onConnectionSuspended: Called when the client is temporarily in a disconnected state");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        GGGLogImpl.log("onConnectionFailed");

        switch (connectionResult.getErrorCode()) {
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                GGGLogImpl.log("Necesita actualizar google play");
                break;
            case ConnectionResult.SERVICE_MISSING_PERMISSION:
                GGGLogImpl.log("Faltan permisos: ACCESS_FINE_LOCATION");
                break;
            default:
                break;
        }
    }

    public GoogleApiClient getGoogleApiClient() {
        return client;
    }

    public interface OnConnectedListener {
        void onConnected(Bundle bundle);
    }

    public void setOnConnectedListener(OnConnectedListener onConnectedListener) {
        this.onConnectedListener = onConnectedListener;
    }
}
