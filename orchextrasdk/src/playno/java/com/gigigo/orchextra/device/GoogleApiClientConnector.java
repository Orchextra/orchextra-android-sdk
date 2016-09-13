package com.gigigo.orchextra.device;

import android.os.Bundle;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//TODO gcmtest
public interface GoogleApiClientConnector //extends GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
{
    void connect();

    Object getGoogleApiClient();

    boolean isConnected();

    void disconnected();

    boolean isGoogleApiClientAvailable();

    void setOnConnectedListener(OnConnectedListener onConnectedListener);

    interface OnConnectedListener {
        void onConnected(Bundle bundle);

       // void onConnectionFailed(ConnectionResult connectionResult);
    }
}
