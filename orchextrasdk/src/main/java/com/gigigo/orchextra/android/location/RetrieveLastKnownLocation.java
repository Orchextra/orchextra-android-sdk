package com.gigigo.orchextra.android.location;

import android.location.Location;
import android.os.Bundle;

import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.orchextra.android.googleClient.GoogleApiClientConnector;
import com.gigigo.orchextra.android.permissions.PermissionLocationImp;
import com.google.android.gms.location.LocationServices;

public class RetrieveLastKnownLocation {

    private final ContextProvider contextProvider;
    private final GoogleApiClientConnector googleApiClientConnector;
    private final PermissionChecker permissionChecker;
    private final PermissionLocationImp accessFineLocationPermissionImp;

    private OnLastKnownLocationListener onLastKnownLocationListener;

    public RetrieveLastKnownLocation(ContextProvider contextProvider,
                                     GoogleApiClientConnector googleApiClientConnector,
                                     PermissionChecker permissionChecker,
                                     PermissionLocationImp accessFineLocationPermissionImp) {

        this.contextProvider = contextProvider;
        this.googleApiClientConnector = googleApiClientConnector;
        this.permissionChecker = permissionChecker;
        this.accessFineLocationPermissionImp = accessFineLocationPermissionImp;
    }

    public void getLastKnownLocation(OnLastKnownLocationListener onLastKnownLocationListener) {
        this.onLastKnownLocationListener = onLastKnownLocationListener;
        googleApiClientConnector.setOnConnectedListener(onConnectedListener);
        googleApiClientConnector.connect();
    }

    private GoogleApiClientConnector.OnConnectedListener onConnectedListener = new GoogleApiClientConnector.OnConnectedListener() {
        @Override
        public void onConnected(Bundle bundle) {
            permissionChecker.askForPermission(accessFineLocationPermissionImp, userPermissionResponseListener, contextProvider.getCurrentActivity());
            getUserLocation();
        }
    };

    @SuppressWarnings("ResourceType")
    private void getUserLocation() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClientConnector.getGoogleApiClient());
        if (onLastKnownLocationListener != null) {
            onLastKnownLocationListener.onLastKnownLocation(lastLocation);
        }
    }

    private UserPermissionRequestResponseListener userPermissionResponseListener =
            new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed) {
            getUserLocation();
        }
    };

    public interface OnLastKnownLocationListener {
        void onLastKnownLocation(Location location);
    }
}
