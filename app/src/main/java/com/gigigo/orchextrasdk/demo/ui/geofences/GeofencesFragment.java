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

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gigigo.orchextrasdk.demo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;

public class GeofencesFragment extends Fragment implements OnMapReadyCallback {

  @ColorInt public static final int fillColor = 0x555677FC;
  @ColorInt public static final int strokeColor = 0xFF5677FC;
  int zoomLevel = 15;

  private MapView mapView;
  GoogleMap googleMap;
  private FusedLocationProviderClient fusedLocationClient;

  public GeofencesFragment() {
  }

  public static GeofencesFragment newInstance() {
    return new GeofencesFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_geofences, container, false);
    mapView = view.findViewById(R.id.mapView);

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
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
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

  @Override public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;

    getGeofences();
    showMyLocation();
  }

  private void showMyLocation() {
    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }

    fusedLocationClient.getLastLocation()
        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
          @Override public void onSuccess(Location location) {
            if (location != null) {
              LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
              CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel);
              googleMap.addMarker(new MarkerOptions().position(latLng)
                  .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_myposition)));

              googleMap.animateCamera(cameraUpdate);
            }
          }
        });
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

  void drawGeofences(List<Geofence> geofences) {
    googleMap.clear();

    BitmapDescriptor markerIcon =
        getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.geofence_marker));

    for (Geofence geofence : geofences) {
      googleMap.addMarker(
          new MarkerOptions().position(geofence.getCenter()).icon(markerIcon).anchor(0.5f, 0.5f));

      googleMap.addCircle(new CircleOptions().center(geofence.getCenter())
          .radius(geofence.getRadius())
          .strokeWidth(2f)
          .strokeColor(strokeColor)
          .fillColor(fillColor));
    }
  }

  private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
    Canvas canvas = new Canvas();
    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888);
    canvas.setBitmap(bitmap);
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    drawable.draw(canvas);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }
}
