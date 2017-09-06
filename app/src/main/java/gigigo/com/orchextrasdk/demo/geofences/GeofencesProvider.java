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

package gigigo.com.orchextrasdk.demo.geofences;

import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.domain.entities.GeoMarketing;
import com.gigigo.orchextra.core.domain.entities.Point;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public final class GeofencesProvider {

  public GeofencesProvider() {
  }

  public void getGeofences(GeofencesProviderCallback geofencesProviderCallback) {

    Orchextra orchextra = Orchextra.INSTANCE;
    List<GeoMarketing> geoMarketings =
        orchextra.getTriggerManager().getConfiguration().getGeoMarketing();

    List<Geofence> geofences = map(geoMarketings);

    geofencesProviderCallback.onGetGeofencesSuccess(geofences);
  }

  private List<Geofence> map(List<GeoMarketing> geoMarketings) {

    List<Geofence> geofences = new ArrayList<>();
    Point point;

    for (GeoMarketing geoMarketing : geoMarketings) {
      point = geoMarketing.getPoint();
      geofences.add(
          new Geofence(new LatLng(point.getLat(), point.getLng()), geoMarketing.getRadius()));
    }

    return geofences;
  }

  public interface GeofencesProviderCallback {

    void onGetGeofencesSuccess(List<Geofence> geofences);

    void onGetGeofencesError(String error);
  }
}
