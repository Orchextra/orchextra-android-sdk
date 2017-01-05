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

package com.gigigo.orchextra.device.geolocation.location;

import android.location.Location;
import android.os.Bundle;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;

public class RetrieveLastKnownLocation {

  private final ContextProvider contextProvider;
  private final GoogleApiClientConnector googleApiClientConnector;
  private final PermissionChecker permissionChecker;
  private final PermissionLocationImp accessFineLocationPermissionImp;
  private final RetrieveLocationByGpsOrNetworkProvider retrieveLocationByGpsOrNetworkProvider;

  private OnLastKnownLocationListener onLastKnownLocationListener;

  public RetrieveLastKnownLocation(ContextProvider contextProvider,
      GoogleApiClientConnector googleApiClientConnector,
      RetrieveLocationByGpsOrNetworkProvider retrieveLocationByGpsOrNetworkProvider,
      PermissionChecker permissionChecker, PermissionLocationImp accessFineLocationPermissionImp) {

    this.contextProvider = contextProvider;
    this.googleApiClientConnector = googleApiClientConnector;
    this.retrieveLocationByGpsOrNetworkProvider = retrieveLocationByGpsOrNetworkProvider;
    this.permissionChecker = permissionChecker;
    this.accessFineLocationPermissionImp = accessFineLocationPermissionImp;
  }

  public void getLastKnownLocation(OnLastKnownLocationListener onLastKnownLocationListener) {
    this.onLastKnownLocationListener = onLastKnownLocationListener;
    googleApiClientConnector.setOnConnectedListener(onConnectedListener);
    googleApiClientConnector.connect();
  }

  private GoogleApiClientConnector.OnConnectedListener onConnectedListener =
      new GoogleApiClientConnector.OnConnectedListener() {
        @Override public void onConnected(Bundle bundle) {
          askPermissionAndGetLastKnownLocation();
        }
      };

  public void askPermissionAndGetLastKnownLocation() {
    boolean isGranted = true;
    if (isGranted) {
      getLastKnownLocation();
    }
  }

  @SuppressWarnings("ResourceType")
  //TODO gcmtest
  private void getLastKnownLocation() {
    Location location = retrieveLocationByGpsOrNetworkProvider.retrieveLocation();

    if (location == null) {
      location = new Location("OxFakeProviderGGGMadLocation");
      location.setLatitude(0.0);//-3.6281034
      location.setLongitude(0.0);//40.4458428
    }

    if (onLastKnownLocationListener != null) {
      onLastKnownLocationListener.onLastKnownLocation(location);
    }
  }

  public interface OnLastKnownLocationListener {
    void onLastKnownLocation(Location location);
  }
}
