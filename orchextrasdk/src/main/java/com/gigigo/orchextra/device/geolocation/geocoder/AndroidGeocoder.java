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

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotationox.Nullable;

import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;

import java.io.IOException;
import java.util.List;

public class AndroidGeocoder {

    private final Context context;

    public AndroidGeocoder(Context context) {
        this.context = context;
    }

    public GeoLocation getLocation(Location location) {
        return getLocation(location.getLatitude(), location.getLongitude());
    }

    public GeoLocation getLocation(double lat, double lng) {
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
            GeoLocation geoLocation = processAddress(addressList);
            return geoLocation;
        } catch (IOException e) {
        }
        return null;
    }

    @Nullable
    public GeoLocation processAddress(List<Address> addressList) {
        if (addressList != null && addressList.size() > 0) {
            Address address = addressList.get(0);

            GeoLocation geoLocation = new GeoLocation();

            OrchextraLocationPoint point = new OrchextraLocationPoint();
            point.setLat(address.getLatitude());
            point.setLng(address.getLongitude());
            geoLocation.setPoint(point);

            if (address.getMaxAddressLineIndex() > 0) {
                geoLocation.setStreet(address.getAddressLine(0));
            }

            geoLocation.setLocality(address.getLocality());
            geoLocation.setZip(address.getPostalCode());
            geoLocation.setCountry(address.getCountryName());
            geoLocation.setCountryCode(address.getCountryCode());

            return geoLocation;
        }
        return null;
    }
}
