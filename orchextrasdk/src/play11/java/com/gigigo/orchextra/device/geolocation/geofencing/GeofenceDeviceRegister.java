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

package com.gigigo.orchextra.device.geolocation.geofencing;

import android.support.annotation.NonNull;
import android.widget.Toast;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofenceUpdates;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class GeofenceDeviceRegister implements ResultCallback<Status>, OnCompleteListener<Void> {

  private final ContextProvider contextProvider;
  private final GeofencePendingIntentCreator geofencePendingIntentCreator;
  private final GoogleApiClientConnector googleApiClientConnector;
  private final PermissionChecker permissionChecker;
  private final PermissionLocationImp accessFineLocationPermissionImp;
  private final AndroidGeofenceConverter androidGeofenceConverter;
  private final OrchextraLogger orchextraLogger;

  private GeofencingClient mGeofencingClient; //asv new
  //private PendingGeofenceTask mPendingGeofenceTask = PendingGeofenceTask.NONE;//asv new no se q es

  private OrchextraGeofenceUpdates geofenceUpdates;

  public GeofenceDeviceRegister(ContextProvider contextProvider,
      GoogleApiClientConnector googleApiClientConnector,
      GeofencePendingIntentCreator geofencePendingIntentCreator,
      PermissionChecker permissionChecker, PermissionLocationImp accessFineLocationPermissionImp,
      AndroidGeofenceConverter androidGeofenceConverter, OrchextraLogger orchextraLogger) {

    this.contextProvider = contextProvider;
    this.googleApiClientConnector = googleApiClientConnector;
    this.geofencePendingIntentCreator = geofencePendingIntentCreator;
    this.permissionChecker = permissionChecker;
    this.accessFineLocationPermissionImp = accessFineLocationPermissionImp;
    this.androidGeofenceConverter = androidGeofenceConverter;
    this.orchextraLogger = orchextraLogger;

    this.mGeofencingClient =
        LocationServices.getGeofencingClient(contextProvider.getApplicationContext());
  }

  public void register(OrchextraGeofenceUpdates geofenceUpdates) {
    this.geofenceUpdates = geofenceUpdates;
    //todo estos de usar el google api client no chuta ya en 11 no hace la actualizacion
    //    y aunk le programe un actualizador de posicion el DOZE se ocupa de q no haga el idiota y drene la bateria
    //    ahora lo que hay q utilizar es otro objeto direcatmten de geofences para registrar las geofences y registrar los cambios de posicion
    //https:github.com/googlesamples/android-play-location/blob/master/Geofencing/app/src/main/java/com/google/android/gms/location/sample/geofencing/MainActivity.java

    //mGeofencingClient =
    //    LocationServices.getGeofencingClient(contextProvider.getApplicationContext());

        registerGeofencesOnDevice();

  }

  //asv new

  //result -> LocationServices.getGeofencingClient(contextProvider.getApplicationContext());

  @Override public void onComplete(Task<Void> task) {
    try {
      //Toast.makeText(contextProvider.getApplicationContext(), "onComplete geofenceregister",
      //    Toast.LENGTH_SHORT).show();


      //todo feature status
    //  registerGeofencesOnDevice();

     //// mPendingGeofenceTask = PendingGeofenceTask.NONE;
     // if (task.isSuccessful()) {
     //     //updateGeofencesAdded(!getGeofencesAdded());
     //     //setButtonsEnabledState();
     //     //
     //     //int messageId = getGeofencesAdded() ? R.string.geofences_added :
     //     //    R.string.geofences_removed;
     //
     // } else {
     //     //// Get the status code for the error and log it using a user-friendly message.
     //     //String errorMessage = GeofenceErrorMessages.getErrorString(this, task.getException());
     //     //Log.w ("", errorMessage);
     // }
    } catch (Throwable throwable) {
    }
  }

  private void registerGeofencesOnDevice() {
    boolean isGranted = permissionChecker.isGranted(accessFineLocationPermissionImp);
    if (isGranted) {
      registerGeofence();
    } else {
      if (contextProvider.getCurrentActivity() != null) {
        permissionChecker.askForPermission(accessFineLocationPermissionImp,
            userPermissionResponseListener, contextProvider.getCurrentActivity());
      }
    }
  }

  /*@Override public void onResult(Status status) {
    if (status.isSuccess()) {
      orchextraLogger.log("Registered Geofences Success!", OrchextraSDKLogLevel.DEBUG);
    } else if (status.hasResolution()) {
      Activity currentActivity = contextProvider.getCurrentActivity();
      if (currentActivity != null) {
        try {
          status.startResolutionForResult(currentActivity, status.getStatusCode());
        } catch (IntentSender.SendIntentException e) {
          orchextraLogger.log("Geofences Handle resolution!", OrchextraSDKLogLevel.DEBUG);
        }
      }
    } else if (status.isCanceled()) {
      orchextraLogger.log("Registered Geofences Canceled!", OrchextraSDKLogLevel.DEBUG);
    } else if (status.isInterrupted()) {
      orchextraLogger.log("Registered Geofences Interrupted!", OrchextraSDKLogLevel.DEBUG);
    }

    if (googleApiClientConnector.isConnected()) {
      googleApiClientConnector.disconnected();
    }
  }
*//*
  private GoogleApiClientConnector.OnConnectedListener onConnectedRegisterGeofenceListener =
      new GoogleApiClientConnector.OnConnectedListener() {
        @Override public void onConnected() {
          registerGeofencesOnDevice();
        }

        @Override public void onConnectionFailed() {
          orchextraLogger.log(
              "No se ha podido conectar GoogleApiClientConnector en las peticion de las geofences");
        }
      };
*/
  private UserPermissionRequestResponseListener userPermissionResponseListener =
      new UserPermissionRequestResponseListener() {
        @Override public void onPermissionAllowed(boolean permissionAllowed) {
          if (permissionAllowed) {
            registerGeofence();
          }
        }
      };

  @SuppressWarnings("ResourceType") private void registerGeofence() {
    try {
      orchextraLogger.log(
          "Removing " + geofenceUpdates.getDeleteGeofences().size() + " geofences...");
      orchextraLogger.log(
          "Registering " + geofenceUpdates.getNewGeofences().size() + " geofences...");

      List<String> deleteCodeList =
          androidGeofenceConverter.getCodeList(geofenceUpdates.getDeleteGeofences());

      if (deleteCodeList.size() > 0) {
        //LocationServices.GeofencingApi.removeGeofences(
        //    googleApiClientConnector.getGoogleApiClient(), deleteCodeList);

        mGeofencingClient.removeGeofences(deleteCodeList).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override public void onComplete(@NonNull Task<Void> task) {
            Toast.makeText(contextProvider.getApplicationContext(), "onComplete geofence remove",
                Toast.LENGTH_SHORT).show();
          }
        });


      }

      if (geofenceUpdates.getNewGeofences().size() > 0) {

        GeofencingRequest geofencingRequest =
                androidGeofenceConverter.convertGeofencesToGeofencingRequest(
                    geofenceUpdates.getNewGeofences());

        mGeofencingClient.addGeofences(geofencingRequest, geofencePendingIntentCreator.getGeofencingPendingIntent())
            .addOnCompleteListener(this);


        //GeofencingRequest geofencingRequest =
        //    androidGeofenceConverter.convertGeofencesToGeofencingRequest(
        //        geofenceUpdates.getNewGeofences());
        //
        //if (googleApiClientConnector.isGoogleApiClientAvailable()) {
        //  try {
        //    LocationServices.GeofencingApi.addGeofences(
        //        googleApiClientConnector.getGoogleApiClient(), geofencingRequest,
        //        geofencePendingIntentCreator.getGeofencingPendingIntent()).setResultCallback(this);
        //
        //    //LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClientConnector,;
        //  } catch (Exception e) {
        //    orchextraLogger.log("Exception trying to add geofences: " + e.getMessage(),
        //        OrchextraSDKLogLevel.ERROR);
        //  }
        //}
      }
    } catch (Throwable tr) {
      if (orchextraLogger != null) orchextraLogger.log("Error orch 593" + tr.getStackTrace());
    }
  }

  public void clean() {
    //todo esto hay q revisarlo, ya q no sé si borra todas las geofences
    mGeofencingClient.removeGeofences(geofencePendingIntentCreator.getGeofencingPendingIntent()).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override public void onComplete(@NonNull Task<Void> task) {
        //Toast.makeText(contextProvider.getApplicationContext(), "onComplete geofence CLEAN ALL",
        //    Toast.LENGTH_SHORT).show();
      }
    });


    //
    //if (googleApiClientConnector.isConnected()) {
    //  clearGeofences();
    //} else {
    //  googleApiClientConnector.setOnConnectedListener(onConnectedRemoveGeofenceListener);
    //  googleApiClientConnector.connect();
    //}
  }

  @Override public void onResult(@NonNull Status status) {
    //todo

    //Toast.makeText(contextProvider.getApplicationContext(), "onResult geofenceregister",
    //    Toast.LENGTH_SHORT).show();
  }

  //private void clearGeofences() {
  //  if (googleApiClientConnector.isConnected()) {
  //    System.out.println("Geofence Clear");
  //    LocationServices.GeofencingApi.removeGeofences(googleApiClientConnector.getGoogleApiClient(),
  //        geofencePendingIntentCreator.getGeofencingPendingIntent());
  //  }
  //}

  //private GoogleApiClientConnector.OnConnectedListener onConnectedRemoveGeofenceListener =
  //    new GoogleApiClientConnector.OnConnectedListener() {
  //      @Override public void onConnected() {
  //        clearGeofences();
  //      }
  //
  //      @Override public void onConnectionFailed() {
  //        orchextraLogger.log(
  //            "No se ha podido conectar GoogleApiClientConnector en las peticion de las geofences");
  //      }
  //    };
}
