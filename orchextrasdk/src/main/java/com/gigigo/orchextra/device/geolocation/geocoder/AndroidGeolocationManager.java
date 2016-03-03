/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.device.geolocation.geocoder;

import android.location.Location;

import com.gigigo.orchextra.device.geolocation.location.RetrieveLastKnownLocation;
import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;
import com.gigigo.orchextra.domain.abstractions.device.RetrieveGeolocationListener;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;

public class AndroidGeolocationManager implements GeolocationManager {

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


    public void setRetrieveGeolocationListener(RetrieveGeolocationListener retrieveGeolocationListener) {
        this.retrieveGeolocationListener = retrieveGeolocationListener;
    }
}
