/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextrasdk.demo.ui.geofences;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public final class GeofencesProvider {

  public GeofencesProvider() {
  }

  public void getGeofences(GeofencesProviderCallback geofencesProviderCallback) {

    List<Geofence> geofences = new ArrayList<>();
    geofences.add(new Geofence(new LatLng(40.451620, -3.629468), 131));
    geofences.add(new Geofence(new LatLng(40.450102, -3.633448), 131));
    geofences.add(new Geofence(new LatLng(40.441521, -3.628539), 131));
    geofences.add(new Geofence(new LatLng(40.450664, -3.623676), 131));
    geofences.add(new Geofence(new LatLng(40.445199, -3.622751), 131));

    geofencesProviderCallback.onGetGeofencesSuccess(geofences);
  }

  public interface GeofencesProviderCallback {

    void onGetGeofencesSuccess(List<Geofence> geofences);

    void onGetGeofencesError(String error);
  }
}
