package com.gigigo.orchextra.utils.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.gigigo.orchextra.utils.googleClient.GoogleApiClientConnector;
import com.google.android.gms.location.LocationServices;

public class LocationManager {

    private final Context context;
    private final GoogleApiClientConnector googleApiClientConnector;
    private Location lastLocation;
    private OnRetrieveLastKnownLocation onRetrieveLastKnownLocation;

    public LocationManager(Context context, GoogleApiClientConnector googleApiClientConnector) {
        this.context = context;
        this.googleApiClientConnector = googleApiClientConnector;
    }

    public void initClient() {
        googleApiClientConnector.setOnConnectedListener(onConnectedListener);
        googleApiClientConnector.initGoogleApiClient();
    }

    private GoogleApiClientConnector.OnConnectedListener onConnectedListener =
            new GoogleApiClientConnector.OnConnectedListener() {
                @Override
                public void onConnected(Bundle bundle) {
                    getLastKnownLocation();

                }
            };

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClientConnector.getGoogleApiClient());
        if (onRetrieveLastKnownLocation != null) {
            onRetrieveLastKnownLocation.getLastKnownLocation(lastLocation);
        }
    }

    public float calculateDistance(Location newLocation) {
        if (lastLocation != null) {
            return lastLocation.distanceTo(newLocation);
        } else {
            return 0;
        }
    }

    public interface OnRetrieveLastKnownLocation {
        void getLastKnownLocation(Location location);
    }

    public void setOnRetrieveLastKnownLocation(OnRetrieveLastKnownLocation onRetrieveLastKnownLocation) {
        this.onRetrieveLastKnownLocation = onRetrieveLastKnownLocation;
    }
}
