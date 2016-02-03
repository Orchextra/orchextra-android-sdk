package com.gigigo.orchextra.android.location;

import android.location.Location;

import com.gigigo.orchextra.domain.entities.GeoLocation;

public class AndroidGeolocationManager {

    private final RetrieveLastKnownLocation retrieveLastKnownLocation;
    private final AndroidGeocoder androidGeocoder;

    private RetrieveGeolocationListener retrieveGeolocationListener;

    public AndroidGeolocationManager(RetrieveLastKnownLocation retrieveLastKnownLocation,
                                     AndroidGeocoder androidGeocoder) {

        this.retrieveLastKnownLocation = retrieveLastKnownLocation;
        this.androidGeocoder = androidGeocoder;
    }

    public void retrieveGeolocation() {
        retrieveLastKnownLocation.getLastKnownLocation(onLastKnownLocationListener);
    }

    private RetrieveLastKnownLocation.OnLastKnownLocationListener onLastKnownLocationListener =
            new RetrieveLastKnownLocation.OnLastKnownLocationListener() {
                @Override
                public void onLastKnownLocation(Location location) {
                    GeoLocation geoLocation = null;
                    if (location != null) {
                        geoLocation = androidGeocoder.getLocation(location);
                    }
                    if (retrieveGeolocationListener != null) {
                        retrieveGeolocationListener.retrieveGeolocation(geoLocation);
                    }
                }
            };

    public interface RetrieveGeolocationListener {
        void retrieveGeolocation(GeoLocation geoLocation);
    }

    public void setRetrieveGeolocationListener(RetrieveGeolocationListener retrieveGeolocationListener) {
        this.retrieveGeolocationListener = retrieveGeolocationListener;
    }
}
