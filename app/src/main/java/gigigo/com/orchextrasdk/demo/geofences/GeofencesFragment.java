package gigigo.com.orchextrasdk.demo.geofences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import gigigo.com.orchextrasdk.R;

public class GeofencesFragment extends Fragment implements OnMapReadyCallback {

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

  @Override public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;

    LatLng sydney = new LatLng(-34, 151);
    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
  }
}
