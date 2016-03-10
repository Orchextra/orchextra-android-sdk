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

package com.gigigo.orchextra.device.geolocation.geofencing.mapper;

import android.location.Location;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

public class LocationMapper implements ExternalClassToModelMapper<Location, OrchextraPoint> {

    @Override public OrchextraPoint externalClassToModel(Location location) {
        OrchextraPoint point = new OrchextraPoint();

        point.setLat(location.getLatitude());
        point.setLng(location.getLongitude());

        return point;
    }
}
