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

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import gigigo.com.orchextrasdk.R;
import java.util.List;

public class GeofencesFragment extends Fragment implements OnMapReadyCallback {

  @ColorInt public static final int fillColor = 0x555677FC;
  @ColorInt public static final int strokeColor = 0xFF5677FC;

  private MapView mapView;
  private GoogleMap googleMap;

  public GeofencesFragment() {
  }

  public static GeofencesFragment newInstance() {
    return new GeofencesFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_geofences, container, false);
    mapView = (MapView) view.findViewById(R.id.mapView);

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mapView.onCreate(savedInstanceState);

    try {
      MapsInitializer.initialize(getActivity().getApplicationContext());
    } catch (Exception e) {
      e.printStackTrace();
    }

    mapView.getMapAsync(this);
  }

  @Override public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @SuppressWarnings("MissingPermission") @Override public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
    this.googleMap.setMyLocationEnabled(true);

    getGeofences();
  }

  private void getGeofences() {
    new GeofencesProvider().getGeofences(new GeofencesProvider.GeofencesProviderCallback() {
      @Override public void onGetGeofencesSuccess(List<Geofence> geofences) {
        drawGeofences(geofences);
      }

      @Override public void onGetGeofencesError(String error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void drawGeofences(List<Geofence> geofences) {

    googleMap.clear();

    for (Geofence geofence : geofences) {
      googleMap.addCircle(new CircleOptions().center(geofence.getCenter())
          .radius(geofence.getRadius())
          .strokeWidth(2f)
          .strokeColor(strokeColor)
          .fillColor(fillColor));
    }
  }
}
