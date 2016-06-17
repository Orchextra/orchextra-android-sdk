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

package com.gigigo.orchextra.device;

import android.os.Bundle;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.device.permissions.GoogleApiPermissionChecker;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.sdk.features.GooglePlayServicesStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GoogleApiClientConnectorImp
    implements GoogleApiClientConnector {

  private final ContextProvider contextProvider;
  private final GoogleApiPermissionChecker googleApiPermissionChecker;
  private final OrchextraLogger orchextraLogger;

  private GoogleApiClient client;
  private OnConnectedListener onConnectedListener;

  public GoogleApiClientConnectorImp(ContextProvider contextProvider,
                                     GoogleApiPermissionChecker googleApiPermissionChecker, OrchextraLogger orchextraLogger) {
    this.contextProvider = contextProvider;
    this.googleApiPermissionChecker = googleApiPermissionChecker;
    this.orchextraLogger = orchextraLogger;
  }

  @Override
  public void connect() {
    if (contextProvider.getApplicationContext() != null
        && googleApiPermissionChecker.checkPlayServicesStatus() == ConnectionResult.SUCCESS) {
      client = new GoogleApiClient.Builder(
          contextProvider.getApplicationContext()).addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();
      client.connect();
    }
  }

  @Override public void onConnected(Bundle bundle) {
    orchextraLogger.log("onConnected");

    if (onConnectedListener != null) {
      onConnectedListener.onConnected(bundle);
    }
  }

  @Override public void onConnectionSuspended(int cause) {
    orchextraLogger.log(
        "onConnectionSuspended: Called when the client is temporarily in a disconnected state");
  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {
    orchextraLogger.log("onConnectionFailed");
    orchextraLogger.log(connectionResult.toString());
  }

  @Override
  public GoogleApiClient getGoogleApiClient() {
    return client;
  }

  @Override
  public boolean isConnected() {
    return client != null && client.isConnected();
  }

  @Override
  public void disconnected() {
    if (client != null) {
      client.disconnect();
    }
  }

  @Override
  public boolean isGoogleApiClientAvailable() {

    GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

    int status = googleApiAvailability.isGooglePlayServicesAvailable(contextProvider.getApplicationContext());

    GooglePlayServicesStatus gpss = GooglePlayServicesStatus.getGooglePlayServicesStatus(status);

    if (gpss == GooglePlayServicesStatus.SUCCESS) {
      if (isConnected()) {
        return true;
      } else {
        orchextraLogger.log("GoogleApiClientConnector connection Status: " + isConnected(),
            OrchextraSDKLogLevel.ERROR);

        return false;
      }
    } else {
      orchextraLogger.log("Google play services not ready, Status: " + gpss.getStringValue(),
          OrchextraSDKLogLevel.ERROR);
      return false;
    }
  }

  @Override
  public void setOnConnectedListener(OnConnectedListener onConnectedListener) {
    this.onConnectedListener = onConnectedListener;
  }
}
