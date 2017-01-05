package com.gigigo.orchextra.device;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public interface GoogleApiClientConnector extends GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    void connect();

    GoogleApiClient getGoogleApiClient();

    boolean isConnected();

    void disconnected();

    boolean isGoogleApiClientAvailable();

    void setOnConnectedListener(OnConnectedListener onConnectedListener);

    interface OnConnectedListener {
        void onConnected();

        void onConnectionFailed();
    }
}
