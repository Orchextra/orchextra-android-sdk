package com.gigigo.orchextra.device.geolocation.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

public class RetrieveLocationByGpsOrNetworkProvider {

  private final Context context;

  public RetrieveLocationByGpsOrNetworkProvider(Context context) {
    this.context = context;
  }

  @SuppressWarnings("ResourceType") public Location retrieveLocation() {
    Location location = null;

    try {
      LocationManager locationManager =
          (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

      boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

      boolean isNetworkEnabled =
          locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

      if (isNetworkEnabled) {
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener,
            Looper.getMainLooper());
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      }

      if (isGPSEnabled) {
        if (location == null) {
          locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, Looper.getMainLooper());
          location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
      }
    } catch (Exception ignored) {
      ignored.printStackTrace();
    }

    return location;
  }

  private LocationListener locationListener = new LocationListener() {
    @Override public void onLocationChanged(Location location) {

    }

    @Override public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override public void onProviderEnabled(String provider) {

    }

    @Override public void onProviderDisabled(String provider) {

    }
  };
}
