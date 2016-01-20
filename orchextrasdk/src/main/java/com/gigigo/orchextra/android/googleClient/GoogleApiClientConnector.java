package com.gigigo.orchextra.android.googleClient;

import android.os.Bundle;

import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.android.permissions.PermissionLocationImp;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GoogleApiClientConnector implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final ContextProvider contextProvider;
    private final PermissionChecker permissionChecker;
    private final PermissionLocationImp accessFineLocationPermissionImp;

    private GoogleApiClient client;
    private OnConnectedListener onConnectedListener;

    public GoogleApiClientConnector(ContextProvider contextProvider, PermissionChecker permissionChecker,
                                    PermissionLocationImp accessFineLocationPermissionImp) {
        this.contextProvider = contextProvider;
        this.permissionChecker =  permissionChecker;
        this.accessFineLocationPermissionImp = accessFineLocationPermissionImp;
    }

    public void initGoogleApiClient() {
        if (contextProvider.getApplicationContext() != null) {
            client = new GoogleApiClient.Builder(contextProvider.getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            client.connect();
        }
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
                if (contextProvider.getCurrentActivity() != null) {
                    permissionChecker.askForPermission(accessFineLocationPermissionImp, userPermissionResponseListener, contextProvider.getCurrentActivity());
                }
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

    private UserPermissionRequestResponseListener userPermissionResponseListener = new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed) {
            if (permissionAllowed) {
                initGoogleApiClient();
            }
        }
    };
}
